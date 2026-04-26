package com.anthropic.claude.chat.parse

import com.anthropic.claude.api.chat.messages.ContentBlockDeltaEvent
import com.anthropic.claude.api.chat.messages.ContentBlockStartEvent
import com.anthropic.claude.api.chat.messages.MessageDeltaEvent
import com.anthropic.claude.api.chat.messages.MessageStartEvent
import com.anthropic.claude.api.chat.messages.StreamEvent
import com.anthropic.claude.api.chat.messages.TextDelta
import com.anthropic.claude.api.chat.messages.ThinkingDelta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Concrete implementation of [SseEventProcessor].
 *
 * Accumulates content block deltas keyed by block index into reactive state flows.
 * Exposes [assembledText] for the UI layer to collect and render incrementally.
 */
class SseEventProcessorImpl : SseEventProcessor {

    private val json = Json { ignoreUnknownKeys = true }

    /** Map of block index → accumulated text so far for that block. */
    private val blockTexts = mutableMapOf<Int, StringBuilder>()

    private val _assembledText = MutableStateFlow("")
    val assembledText: StateFlow<String> = _assembledText.asStateFlow()

    private val _thinking = MutableStateFlow("")
    val thinking: StateFlow<String> = _thinking.asStateFlow()

    private val _isStreaming = MutableStateFlow(false)
    val isStreaming: StateFlow<Boolean> = _isStreaming.asStateFlow()

    private val _stopReason = MutableStateFlow<String?>(null)
    val stopReason: StateFlow<String?> = _stopReason.asStateFlow()

    override suspend fun processEvent(event: StreamEvent) {
        when (event) {
            is MessageStartEvent -> {
                _isStreaming.value = true
                blockTexts.clear()
                _assembledText.value = ""
                _thinking.value = ""
                _stopReason.value = null
            }

            is ContentBlockStartEvent -> {
                val index = event.index ?: return
                blockTexts.getOrPut(index) { StringBuilder() }
            }

            is ContentBlockDeltaEvent -> {
                val index = event.index ?: return
                val deltaJson = event.delta ?: return

                try {
                    val type = deltaJson.jsonObject["type"]?.jsonPrimitive?.content

                    when (type) {
                        "text_delta" -> {
                            val text = deltaJson.jsonObject["text"]?.jsonPrimitive?.content ?: return
                            blockTexts.getOrPut(index) { StringBuilder() }.append(text)
                            _assembledText.value = blockTexts.values.joinToString("") { it.toString() }
                        }
                        "thinking_delta" -> {
                            val thinking = deltaJson.jsonObject["thinking"]?.jsonPrimitive?.content ?: return
                            _thinking.value = _thinking.value + thinking
                        }
                        "input_json_delta" -> {
                            // Tool-use argument accumulation — handled by ToolInvocationHandler
                        }
                        else -> Unit
                    }
                } catch (_: Exception) {
                    // Malformed delta — skip silently
                }
            }

            is MessageDeltaEvent -> {
                try {
                    val stopReason = event.delta?.jsonObject?.get("stop_reason")?.jsonPrimitive?.content
                    if (stopReason != null) {
                        _stopReason.value = stopReason
                    }
                } catch (_: Exception) { }
            }

            else -> {
                // Lifecycle events (MessageStop, ConversationReady, etc.) handled elsewhere
                _isStreaming.value = false
            }
        }
    }

    /** Resets all accumulated state for a new message. */
    fun reset() {
        blockTexts.clear()
        _assembledText.value = ""
        _thinking.value = ""
        _isStreaming.value = false
        _stopReason.value = null
    }
}
