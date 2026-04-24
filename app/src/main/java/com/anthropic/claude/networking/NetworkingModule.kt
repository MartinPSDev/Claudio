package com.anthropic.claude.networking

import com.anthropic.claude.protos.push.LoggedInPushOperationsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Factory that builds the shared [OkHttpClient] and related networking components.
 */
object NetworkingModule {

    fun buildOkHttpClient(
        sessionTokenProvider: () -> String?,
        onSessionExpired: () -> Unit,
        enableLogging: Boolean = false,
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (enableLogging) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        .addInterceptor(SessionInterceptor(sessionTokenProvider))
        .addInterceptor(AuthExpiredInterceptor(onSessionExpired))
        .build()

    fun buildApiClient(
        sessionTokenProvider: () -> String?,
        onSessionExpired: () -> Unit,
        baseUrl: String = AnthropicApiClient.BASE_URL_PRODUCTION,
        enableLogging: Boolean = false,
    ): AnthropicApiClient = AnthropicApiClient(
        baseUrl = baseUrl,
        sessionToken = sessionTokenProvider(),
    )
}
