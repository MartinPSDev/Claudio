package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for submitting a tool result back to the model.
 */
@Serializable
data class RecordToolResultRequest(
    @SerialName("tool_use_id") val toolUseId: String,
    val content: JsonElement? = null,
    @SerialName("is_error") val isError: Boolean = false,
)
