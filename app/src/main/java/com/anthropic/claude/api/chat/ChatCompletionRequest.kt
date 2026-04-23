package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for sending a new message to a chat conversation.
 */
@Serializable
data class ChatCompletionRequest(
    val prompt: String,
    val model: String? = null,
    @SerialName("parent_message_uuid") val parentMessageUuid: String? = null,
    val attachments: List<JsonElement>? = null,
    val files: List<JsonElement>? = null,
    val tools: List<JsonElement>? = null,
    @SerialName("tool_states") val toolStates: JsonElement? = null,
    @SerialName("input_mode") val inputMode: String? = null,
    @SerialName("rendering_mode") val renderingMode: String? = null,
    val timezone: String? = null,
    @SerialName("personalized_styles") val personalizedStyles: JsonElement? = null,
    @SerialName("create_conversation_params") val createConversationParams: JsonElement? = null,
)
