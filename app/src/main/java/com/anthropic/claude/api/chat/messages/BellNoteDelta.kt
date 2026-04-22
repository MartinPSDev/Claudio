package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BellNoteDelta(
    @SerialName("partial_text") val partialText: String,
) : ContentBlockDelta
