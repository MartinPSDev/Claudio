package com.anthropic.claude.repository

import com.anthropic.claude.api.chat.ChatConversation
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.MoveChatsRequest
import com.anthropic.claude.api.chat.UpdateChatRequest
import com.anthropic.claude.db.dao.ConversationDao
import com.anthropic.claude.db.entity.CachedConversationEntity
import com.anthropic.claude.db.entity.ChatIdListEntryEntity
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 * Repository for chat conversations.
 * Single source of truth: Room cache + AnthropicApiClient network calls.
 */
class ConversationRepository(
    private val apiClient: AnthropicApiClient,
    private val conversationDao: ConversationDao,
) {
    private val json = Json { ignoreUnknownKeys = true }

    // ── Observe (Room — reactive) ─────────────────────────────────────────────

    fun observeConversations(orgId: String): Flow<List<CachedConversationEntity>> =
        conversationDao.observeByOrg(orgId)

    fun observeIdList(orgId: String): Flow<List<ChatIdListEntryEntity>> =
        conversationDao.observeIdList(orgId)

    // ── Fetch & cache from network ────────────────────────────────────────────

    suspend fun refreshConversations(orgId: String) {
        try {
            val response = apiClient.getConversations(orgId)
            if (response.isSuccessful) {
                val body = response.body?.string() ?: return
                val conversations = json.decodeFromString<List<ChatConversation>>(body)
                val entities = conversations.map { it.toCachedEntity(orgId) }
                conversationDao.upsertAll(entities)
            }
        } catch (_: Exception) { }
    }

    suspend fun fetchConversation(chatId: String): CachedConversationEntity? =
        conversationDao.getByUuid(chatId)

    // ── Mutations ─────────────────────────────────────────────────────────────

    suspend fun createConversation(orgId: String, request: CreateChatRequest): ApiResult<ChatConversation> =
        execute { apiClient.createChat(orgId, request) }

    suspend fun updateConversation(orgId: String, chatId: String, request: UpdateChatRequest): ApiResult<ChatConversation> =
        execute { apiClient.updateConversation(orgId, chatId, request) }

    suspend fun deleteConversation(orgId: String, chatId: String): ApiResult<Unit> {
        conversationDao.deleteByUuid(chatId)
        return try {
            val response = apiClient.deleteConversation(orgId, chatId)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    suspend fun moveConversations(orgId: String, request: MoveChatsRequest): ApiResult<Unit> =
        try {
            val response = apiClient.moveChats(orgId, request)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private suspend inline fun <reified T> execute(
        crossinline call: suspend () -> okhttp3.Response,
    ): ApiResult<T> = try {
        val response = call()
        val body = response.body?.string() ?: ""
        if (response.isSuccessful) ApiResult.Success(json.decodeFromString(body))
        else ApiResult.Error(response.code, body.takeIf { it.isNotBlank() })
    } catch (e: Exception) {
        ApiResult.NetworkError
    }

    private fun ChatConversation.toCachedEntity(orgId: String) = CachedConversationEntity(
        uuid = uuid,
        orgId = orgId,
        name = name,
        summary = summary,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isStarred = isStarred,
        projectUuid = projectUuid,
    )
}
