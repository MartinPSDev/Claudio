package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request to delete multiple conversations at once.
 */
@Serializable
data class DeleteChatsRequest(
    @SerialName("conversation_uuids") val conversationUuids: List<String>,
)
