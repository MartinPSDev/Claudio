package com.anthropic.claude.repository

import com.anthropic.claude.api.chat.ChatConversation
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.UpdateChatRequest
import com.anthropic.claude.api.chat.MoveChatsRequest
import com.anthropic.claude.db.dao.ConversationDao
import com.anthropic.claude.db.entity.CachedConversationEntity
import com.anthropic.claude.db.entity.ChatIdListEntryEntity
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for chat conversations.
 * Single source of truth: Room cache + AnthropicApiClient network calls.
 */
class ConversationRepository(
    private val apiClient: AnthropicApiClient,
    private val conversationDao: ConversationDao,
) {
    // ── Observe (Room — reactive) ─────────────────────────────────────────────

    fun observeConversations(orgId: String): Flow<List<CachedConversationEntity>> =
        conversationDao.observeByOrg(orgId)

    fun observeIdList(orgId: String): Flow<List<ChatIdListEntryEntity>> =
        conversationDao.observeIdList(orgId)

    // ── Fetch & cache from network ────────────────────────────────────────────

    suspend fun refreshConversations(orgId: String) {
        // TODO: when ApiResult.Success → map to entities and upsert
        // val result = apiClient.getChats(orgId)
        // if (result is ApiResult.Success) {
        //     val entities = result.data.map { it.toCachedEntity(orgId) }
        //     conversationDao.upsertAll(entities)
        // }
    }

    suspend fun fetchConversation(chatId: String): CachedConversationEntity? {
        // TODO: apiClient.getChat(chatId) → upsert → return entity
        return conversationDao.getByUuid(chatId)
    }

    // ── Mutations ─────────────────────────────────────────────────────────────

    suspend fun createConversation(request: CreateChatRequest): ApiResult<ChatConversation> {
        // TODO: val result = apiClient.createChat(request)
        // if (result is ApiResult.Success) conversationDao.upsert(result.data.toCachedEntity())
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun updateConversation(chatId: String, request: UpdateChatRequest): ApiResult<ChatConversation> {
        // TODO: val result = apiClient.updateChat(chatId, request)
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun deleteConversation(chatId: String): ApiResult<Unit> {
        conversationDao.deleteByUuid(chatId)
        // TODO: apiClient.deleteChat(chatId)
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun moveConversations(request: MoveChatsRequest): ApiResult<Unit> {
        // TODO: apiClient.moveChats(request)
        return ApiResult.Error(501, "Not implemented")
    }
}
