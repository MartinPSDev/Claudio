package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** A thinking (extended reasoning) block in a chat message. */
@Serializable
data class ThinkingBlock(
    @SerialName("start_timestamp") val startTimestamp: String? = null,
    @SerialName("stop_timestamp")  val stopTimestamp: String? = null,
    val thinking: String? = null,
    val summaries: JsonElement? = null,
    val flags: JsonElement? = null,
)

/** A voice-note block containing transcribed audio. */
@Serializable
data class VoiceNoteBlock(
    @SerialName("start_timestamp") val startTimestamp: String? = null,
    @SerialName("stop_timestamp")  val stopTimestamp: String? = null,
    val title: String? = null,
    val text: String? = null,
    val flags: JsonElement? = null,
)
