package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for securely filling sensitive form fields in a browser session.
 * Used by the computer-use / remote session agent.
 */
@Serializable
data class FillSensitiveTextRequest(
    val domain: String? = null,
    val fields: List<JsonElement>? = null,
)

/**
 * Request to generate a suggested title for a chat conversation.
 */
@Serializable
data class GenerateChatTitleRequest(
    @SerialName("message_content") val messageContent: String? = null,
    @SerialName("recent_titles") val recentTitles: List<String>? = null,
)
