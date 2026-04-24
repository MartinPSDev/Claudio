package com.anthropic.claude.networking

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Attaches the session cookie and required Anthropic client headers to every request.
 */
class SessionInterceptor(
    private val sessionTokenProvider: () -> String?,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionTokenProvider()
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("anthropic-client-platform", "android")
            .apply { token?.let { header("Cookie", "sessionKey=$it") } }
            .build()
        return chain.proceed(request)
    }
}

/**
 * Intercepts 401 responses and signals that the session has expired.
 */
class AuthExpiredInterceptor(
    private val onSessionExpired: () -> Unit,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401) onSessionExpired()
        return response
    }
}
