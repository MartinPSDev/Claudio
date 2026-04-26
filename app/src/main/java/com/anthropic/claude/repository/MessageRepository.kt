package com.anthropic.claude.repository

import com.anthropic.claude.api.chat.ChatMessage
import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.db.dao.MessageDao
import com.anthropic.claude.db.entity.CachedMessageEntity
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 * Repository for chat messages.
 * Combines Room cache with streaming SSE responses from AnthropicApiClient.
 */
class MessageRepository(
    private val apiClient: AnthropicApiClient,
    private val messageDao: MessageDao,
) {
    private val json = Json { ignoreUnknownKeys = true }

    // ── Observe (Room — reactive) ─────────────────────────────────────────────

    fun observeMessages(conversationUuid: String): Flow<List<CachedMessageEntity>> =
        messageDao.observeByConversation(conversationUuid)

    // ── Fetch & cache ─────────────────────────────────────────────────────────

    suspend fun refreshMessages(orgId: String, conversationUuid: String) {
        try {
            val response = apiClient.getMessages(orgId, conversationUuid)
            if (response.isSuccessful) {
                val body = response.body?.string() ?: return
                val messages = json.decodeFromString<List<ChatMessage>>(body)
                val entities = messages.map { it.toCachedEntity(conversationUuid) }
                messageDao.upsertAll(entities)
            }
        } catch (_: Exception) { }
    }

    // ── Streaming ─────────────────────────────────────────────────────────────

    suspend fun sendMessage(
        orgId: String,
        conversationUuid: String,
        request: ChatCompletionRequest,
        onDelta: (String) -> Unit,
    ): ApiResult<ChatMessage> = try {
        val response = apiClient.sendCompletion(orgId, conversationUuid, request)
        val body = response.body?.string() ?: ""
        if (response.isSuccessful) {
            val message = json.decodeFromString<ChatMessage>(body)
            messageDao.upsert(message.toCachedEntity(conversationUuid))
            ApiResult.Success(message)
        } else {
            ApiResult.Error(response.code, body.takeIf { it.isNotBlank() })
        }
    } catch (e: Exception) {
        ApiResult.NetworkError
    }

    suspend fun retryLastMessage(orgId: String, conversationUuid: String): ApiResult<ChatMessage> =
        try {
            val response = apiClient.retryCompletion(orgId, conversationUuid)
            val body = response.body?.string() ?: ""
            if (response.isSuccessful) ApiResult.Success(json.decodeFromString(body))
            else ApiResult.Error(response.code, body.takeIf { it.isNotBlank() })
        } catch (e: Exception) {
            ApiResult.NetworkError
        }

    // ── Mutations ─────────────────────────────────────────────────────────────

    suspend fun deleteMessage(messageUuid: String) {
        messageDao.deleteByUuid(messageUuid)
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun ChatMessage.toCachedEntity(conversationUuid: String) = CachedMessageEntity(
        uuid = uuid,
        conversationUuid = conversationUuid,
        role = sender,
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
