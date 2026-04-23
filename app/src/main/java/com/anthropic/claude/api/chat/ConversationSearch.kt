package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A chunk of a conversation returned in a search result.
 * Represents a text segment (start/end character offsets) within a conversation document.
 */
@Serializable
data class ConversationSearchChunk(
    @SerialName("doc_uuid") val docUuid: String,
    val start: Int,
    val end: Int,
    val name: String? = null,
    val text: String? = null,
    val extras: ConversationSearchChunkExtras,
)

/**
 * Additional metadata included with each search result chunk.
 */
@Serializable
data class ConversationSearchChunkExtras(
    @SerialName("chat_uuid") val chatUuid: String? = null,
    @SerialName("message_uuid") val messageUuid: String? = null,
    @SerialName("conversation_name") val conversationName: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("sender") val sender: String? = null,
)

/**
 * Paginated search results for conversation search.
 */
@Serializable
data class ConversationSearchResponse(
    val chunks: List<ConversationSearchChunk>? = null,
    @SerialName("has_more") val hasMore: Boolean = false,
    @SerialName("next_cursor") val nextCursor: String? = null,
)
