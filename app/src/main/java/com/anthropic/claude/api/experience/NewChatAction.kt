package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An experience action that opens a new chat, optionally with a pre-filled
 * message and model selection.
 */
@Serializable
data class NewChatAction(
    @SerialName("input_text") val inputText: String? = null,
    @SerialName("model_id") val modelId: String? = null,
)
