package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A conversation with its messages pre-loaded (nested).
 * Used for conversation detail / sharing endpoints.
 */
@Serializable
data class ChatConversationWithNestedMessage(
    val uuid: String,
    val name: String? = null,
    val summary: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("chat_messages") val chatMessages: List<ChatMessage> = emptyList(),
    @SerialName("model") val modelId: String? = null,
    @SerialName("project_uuid") val projectUuid: String? = null,
    @SerialName("is_starred") val isStarred: Boolean = false,
)

/**
 * Search result chunks from conversation search.
 */
@Serializable
data class ConversationSearchChunk(
    val text: String,
    @SerialName("message_uuid") val messageUuid: String? = null,
    @SerialName("conversation_uuid") val conversationUuid: String? = null,
    val score: Float? = null,
    val extras: ConversationSearchChunkExtras? = null,
)

@Serializable
data class ConversationSearchChunkExtras(
    @SerialName("conversation_name") val conversationName: String? = null,
    @SerialName("message_sender") val messageSender: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
)

/**
 * Full search response containing matching chunks.
 */
@Serializable
data class ConversationSearchResponse(
    val results: List<ConversationSearchChunk> = emptyList(),
    @SerialName("total_count") val totalCount: Int = 0,
)

/**
 * Response from bulk-deleting conversations.
 */
@Serializable
data class DeleteChatsResponse(
    @SerialName("deleted_count") val deletedCount: Int = 0,
)

/**
 * Response from removing a message flag.
 */
@Serializable
data class DeleteMessageFlagResponse(
    val success: Boolean = true,
)

/**
 * Response from filling a sensitive text field.
 */
@Serializable
data class FillSensitiveTextResponse(
    @SerialName("filled_text") val filledText: String? = null,
)

/**
 * Response from auto-generating a conversation title.
 */
@Serializable
data class GenerateChatTitleResponse(
    val title: String,
)
