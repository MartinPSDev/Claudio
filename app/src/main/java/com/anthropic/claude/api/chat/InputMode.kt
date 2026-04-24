package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** The input mode used to compose a message. */
@Serializable
enum class InputMode {
    @SerialName("UNKNOWN")       UNKNOWN,
    @SerialName("text")          TEXT,
    @SerialName("speech_input")  SPEECH_INPUT,
    @SerialName("VOICE")         VOICE,
    @SerialName("RETRY")         RETRY,
}
