package com.anthropic.claude.networking

import com.anthropic.claude.api.chat.CreateChatRequest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class AnthropicApiClient(
    private val baseUrl: String = BASE_URL_PRODUCTION,
    private val sessionToken: String? = null,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .apply {
                    sessionToken?.let { header("Cookie", "sessionKey=$it") }
                }
                .build()
            chain.proceed(request)
        }
        .build()

    fun createChat(request: CreateChatRequest): Response {
        val body = json.encodeToString(CreateChatRequest.serializer(), request)
            .toRequestBody(JSON_MEDIA_TYPE)
        val httpRequest = Request.Builder()
            .url("$baseUrl/api/organizations/-/chat_conversations")
            .post(body)
            .build()
        return httpClient.newCall(httpRequest).execute()
    }

    fun getOrganizationConfig(orgId: String): Response {
        val httpRequest = Request.Builder()
            .url("$baseUrl/api/organizations/$orgId")
            .get()
            .build()
        return httpClient.newCall(httpRequest).execute()
    }

    companion object {
        const val BASE_URL_PRODUCTION = "https://claude.ai"
        const val BASE_URL_STAGING = "https://claude-ai.staging.ant.dev"

        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    }
}
