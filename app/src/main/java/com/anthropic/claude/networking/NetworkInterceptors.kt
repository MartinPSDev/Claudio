package com.anthropic.claude.networking

import android.content.Context
import android.os.Build
import androidx.core.os.LocaleListCompat
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Attaches the session cookie and required Anthropic client headers to every request.
 */
class SessionInterceptor(
    private val context: Context,
    private val sessionTokenProvider: () -> String?,
    private val deviceIdProvider: () -> String,
) : Interceptor {

    private val packageName: String by lazy {
        try {
            context.packageName
        } catch (e: Exception) {
            "com.anthropic.claude"
        }
    }

    private val userAgent: String by lazy {
        "Claude $packageName/${Build.VERSION.SDK_INT}/1.260416.20 (Android )"
    }

    private val acceptLanguage: String by lazy {
        val locales = LocaleListCompat.getDefault()
        if (locales.isEmpty) return@lazy "en-US"
        
        val sb = StringBuilder()
        sb.append(locales.get(0)?.toLanguageTag() ?: "en-US")
        
        val size = minOf(locales.size(), 3)
        var q = 1.0f
        for (i in 1 until size) {
            q -= 0.1f
            sb.append(", ")
                .append(locales.get(i)?.toLanguageTag())
                .append(";q=")
                .append(q)
        }
        sb.toString()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionTokenProvider()
        val requestBuilder = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("User-Agent", userAgent)
            .header("Anthropic-Client-Platform", "android")
            .header("Anthropic-Client-App", packageName)
            .header("Anthropic-Client-Version", "1.260416.20")
            .header("Anthropic-Client-OS-Version", Build.VERSION.SDK_INT.toString())
            .header("Anthropic-Device-ID", deviceIdProvider())
            .header("Accept-Language", acceptLanguage)

        token?.let {
            requestBuilder.header("Cookie", "sessionKey=$it")
        }

        return chain.proceed(requestBuilder.build())
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
