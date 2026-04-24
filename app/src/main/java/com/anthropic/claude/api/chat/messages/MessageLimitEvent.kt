package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * SSE event carrying the current message usage limits for the session.
 * Displayed as a usage indicator in the chat UI.
 */
@Serializable
data class MessageLimitEvent(
    @SerialName("message_limit") val messageLimit: JsonElement? = null,
)
