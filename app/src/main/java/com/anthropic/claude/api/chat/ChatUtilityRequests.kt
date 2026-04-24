package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to generate a title for the current chat. */
@Serializable
data class GenerateChatTitleRequest(
    @SerialName("message_content") val messageContent: String? = null,
    @SerialName("recent_titles")   val recentTitles: List<String>? = null,
)

/** Request to fill in sensitive text fields (e.g., form pre-fill). */
@Serializable
data class FillSensitiveTextRequest(
    val domain: String? = null,
    val fields: JsonElement? = null,
)
