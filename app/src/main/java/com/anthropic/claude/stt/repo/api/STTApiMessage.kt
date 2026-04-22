package com.anthropic.claude.stt.repo.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@JsonClassDiscriminator("type")
@Serializable
sealed class STTApiMessage {

    @Serializable
    @SerialName("transcript_text")
    data class TranscriptText(val data: String) : STTApiMessage()

    @Serializable
    @SerialName("unknown")
    data object Unknown : STTApiMessage()
}
