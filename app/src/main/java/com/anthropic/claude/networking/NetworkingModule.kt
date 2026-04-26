package com.anthropic.claude.networking

import android.content.Context
import okhttp3.OkHttpClient
import java.util.UUID

/**
 * Factory that wires together the OkHttpClient, interceptors, and AnthropicApiClient.
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
        sessionTokenProvider: () -> String?,
        deviceIdProvider: () -> String = { UUID.randomUUID().toString() }, // TODO: Inject from datastore
        onAuthExpired: () -> Unit,
    ): AnthropicApiClient {
        val sessionInterceptor = SessionInterceptor(
            context = context,
            sessionTokenProvider = sessionTokenProvider,
            deviceIdProvider = deviceIdProvider
        )
        val authExpiredInterceptor = AuthExpiredInterceptor(onAuthExpired)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(sessionInterceptor)
            .addInterceptor(authExpiredInterceptor)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        return AnthropicApiClient(
            baseUrl = baseUrl,
            httpClient = okHttpClient
        )
    }
}
