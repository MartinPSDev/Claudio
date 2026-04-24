package com.anthropic.claude.repository

import com.anthropic.claude.api.chat.ChatMessage
import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.db.dao.MessageDao
import com.anthropic.claude.db.entity.CachedMessageEntity
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository for chat messages.
 * Combines Room cache with streaming SSE responses from AnthropicApiClient.
 */
class MessageRepository(
    private val apiClient: AnthropicApiClient,
    private val messageDao: MessageDao,
) {
    // ── Observe (Room — reactive) ─────────────────────────────────────────────

    fun observeMessages(conversationUuid: String): Flow<List<CachedMessageEntity>> =
        messageDao.observeByConversation(conversationUuid)

    // ── Fetch & cache ─────────────────────────────────────────────────────────

    suspend fun refreshMessages(conversationUuid: String) {
        // TODO: apiClient.getMessages(conversationUuid) → upsert to Room
    }

    // ── Streaming ─────────────────────────────────────────────────────────────

    suspend fun sendMessage(
        conversationUuid: String,
        request: ChatCompletionRequest,
        onDelta: (String) -> Unit,
    ): ApiResult<ChatMessage> {
        // TODO: open SSE stream via apiClient.streamChatCompletion(conversationUuid, request)
        //   → collect ContentBlockDeltaEvent → call onDelta(text)
        //   → on MessageStopEvent → upsert final message entity to Room
        return ApiResult.Error(501, "Not implemented")
    }

    // ── Mutations ─────────────────────────────────────────────────────────────

    suspend fun deleteMessage(messageUuid: String) {
        messageDao.deleteByUuid(messageUuid)
        // TODO: apiClient.deleteMessage(messageUuid)
    }

    suspend fun regenerateMessage(conversationUuid: String, messageUuid: String): ApiResult<ChatMessage> {
        // TODO: apiClient.regenerateMessage(conversationUuid, messageUuid)
        return ApiResult.Error(501, "Not implemented")
    }
}
