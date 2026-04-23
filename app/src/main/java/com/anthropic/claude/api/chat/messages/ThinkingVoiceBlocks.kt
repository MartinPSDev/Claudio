package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * An extended-thinking block within a chat message.
 */
@Serializable
data class ThinkingBlock(
    @SerialName("start_timestamp") val startTimestamp: Long? = null,
    @SerialName("stop_timestamp") val stopTimestamp: Long? = null,
    val thinking: String? = null,
    val summaries: List<JsonElement>? = null,
    val flags: Int = 0,
)

/**
 * A voice note block transcribed by the STT pipeline.
 */
@Serializable
data class VoiceNoteBlock(
    @SerialName("start_timestamp") val startTimestamp: Long? = null,
    @SerialName("stop_timestamp") val stopTimestamp: Long? = null,
    val title: String? = null,
    val text: String? = null,
    val flags: Int = 0,
)
