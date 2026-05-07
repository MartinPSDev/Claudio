package com.anthropic.claude.networking

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp interceptor that injects the session cookie into requests.
 *
 * Reads the `sessionKey` from [sessionTokenProvider] and adds it
 * as a `Cookie: sessionKey=<value>` header on every API request.
 *
 * Also injects the `X-Device-Id` header for device tracking.
 *
 * Interceptor chain position: after [AnthropicHeaderInterceptor],
 * before [AuthExpiredInterceptor].
 */
class SessionInterceptor(
    private val context: Context,
    private val sessionTokenProvider: () -> String?,
    private val deviceIdProvider: () -> String,
) : Interceptor {

    companion object {
        private const val COOKIE_HEADER = "Cookie"
        private const val SESSION_COOKIE_NAME = "sessionKey"
        private const val DEVICE_ID_HEADER = "X-Device-Id"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        // Inject session cookie
        val sessionKey = sessionTokenProvider()
        if (sessionKey != null) {
            val existingCookies = request.header(COOKIE_HEADER)
            val sessionCookie = "$SESSION_COOKIE_NAME=$sessionKey"

            val cookieValue = if (existingCookies.isNullOrEmpty()) {
                sessionCookie
            } else {
                "$existingCookies; $sessionCookie"
            }
            builder.header(COOKIE_HEADER, cookieValue)
        }

        // Inject device ID
        builder.header(DEVICE_ID_HEADER, deviceIdProvider())

        return chain.proceed(builder.build())
    }
}
