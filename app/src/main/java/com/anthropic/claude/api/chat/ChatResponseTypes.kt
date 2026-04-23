package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.ChatId
import kotlinx.serialization.Serializable

/**
 * Response from the bulk-delete chats endpoint.
 * [deleted] contains IDs successfully removed; [failed] contains those that could not be deleted.
 */
@Serializable
data class DeleteChatsResponse(
    val deleted: Set<ChatId>? = null,
    val failed: Set<ChatId>? = null,
)

/**
 * Response from the bulk-move chats endpoint (moving to/from a project).
 * [moved] contains IDs successfully moved; [failed] contains those that could not be moved.
 */
@Serializable
data class MoveChatsResponse(
    val moved: Set<ChatId>? = null,
    val failed: Set<ChatId>? = null,
)

/** Response from the generate-title endpoint. */
@Serializable
data class GenerateChatTitleResponse(
    val title: String,
)

/** Response from the update-model-fallback endpoint. */
@Serializable
data class UpdateChatModelFallbackResponse(
    val success: Boolean = false,
)

/** Response from the delete-message-flag endpoint. */
@Serializable
data class DeleteMessageFlagResponse(
    val success: Boolean = false,
)
