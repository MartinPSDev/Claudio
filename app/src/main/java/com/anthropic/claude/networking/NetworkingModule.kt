package com.anthropic.claude.networking

import android.content.Context
import okhttp3.OkHttpClient
import java.util.UUID

/**
 * Factory that wires together the OkHttpClient, interceptors, and AnthropicApiClient.
 *
 * Interceptor chain order:
 * 1. [AnthropicHeaderInterceptor] — injects User-Agent, platform headers, device ID, Accept-Language
 * 2. [SessionInterceptor] — injects sessionKey cookie
 * 3. [AuthExpiredInterceptor] — handles 401 → logout callback
 */
object NetworkingModule {

    /**
     * Build and return a fully-configured [AnthropicApiClient].
     *
     * @param context Application context.
     * @param baseUrl The base URL to use (e.g. production or staging).
     * @param sessionTokenProvider Provides the current sessionKey cookie value.
     * @param deviceIdProvider Provides the unique device ID for the header.
     * @param onAuthExpired Callback invoked by [AuthExpiredInterceptor] on 401 responses.
     */
    fun provideApiClient(
        context: Context,
        baseUrl: String = AnthropicApiClient.BASE_URL_PRODUCTION,
        sessionTokenProvider: () -> String? = { null },
        deviceIdProvider: () -> String = { UUID.randomUUID().toString() },
        onAuthExpired: () -> Unit,
    ): AnthropicApiClient {
        val deviceId = deviceIdProvider()

        val headerInterceptor = AnthropicHeaderInterceptor(
            context = context,
            deviceId = deviceId,
        )
        val sessionInterceptor = SessionInterceptor(
            context = context,
            sessionTokenProvider = sessionTokenProvider,
            deviceIdProvider = { deviceId },
        )
        val authExpiredInterceptor = AuthExpiredInterceptor(onAuthExpired)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(sessionInterceptor)
            .addInterceptor(authExpiredInterceptor)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        return AnthropicApiClient(
            baseUrl = baseUrl,
            httpClient = okHttpClient,
        )
    }
}

