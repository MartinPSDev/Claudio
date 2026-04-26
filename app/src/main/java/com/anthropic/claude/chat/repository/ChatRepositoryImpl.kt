package com.anthropic.claude.chat.repository

import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.messages.StreamEvent
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.SseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Concrete implementation of [ChatRepository].
 * Delegates SSE streaming to [SseClient] using requests built via [AnthropicApiClient].
 */
class ChatRepositoryImpl(
    private val apiClient: AnthropicApiClient,
    private val sseClient: SseClient,
    private val organizationId: String,
) : ChatRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }

    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    override fun createChat(request: CreateChatRequest): Flow<StreamEvent> {
        val path = "/api/organizations/$organizationId/chat_conversations"
        val body = json.encodeToString(request).toRequestBody(JSON_MEDIA_TYPE)
        val httpRequest = Request.Builder()
            .url("${AnthropicApiClient.BASE_URL_PRODUCTION}$path")
            .header("x-organization-uuid", organizationId)
            .post(body)
            .build()
        return sseClient.streamEvents(httpRequest)
    }

    override fun sendMessage(chatId: String, request: ChatCompletionRequest): Flow<StreamEvent> {
        val path = "/api/organizations/$organizationId/chat_conversations/$chatId/completion"
        val body = json.encodeToString(request).toRequestBody(JSON_MEDIA_TYPE)
        val httpRequest = Request.Builder()
            .url("${AnthropicApiClient.BASE_URL_PRODUCTION}$path")
            .header("x-organization-uuid", organizationId)
            .post(body)
            .build()
        return sseClient.streamEvents(httpRequest)
    }
}
