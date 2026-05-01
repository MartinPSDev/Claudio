package com.anthropic.claude.networking

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

/**
 * OkHttp interceptor that injects Anthropic-specific request headers
 * on every API request.
 *
 * Headers injected:
 *   - User-Agent: `Claude <packageName>/1.260416.20 (Android <SDK_INT>)`
 *   - Anthropic-Client-Platform: `android`
 *   - Anthropic-Client-App: `<packageName>`
 *   - Anthropic-Client-Version: `1.260416.20`
 *   - Anthropic-Client-OS-Version: `<SDK_INT>`
 *   - Anthropic-Device-ID: `<deviceId>`
 *   - Accept-Language: `<locale>;q=1.0, <locale2>;q=0.9, ...`
 */
class AnthropicHeaderInterceptor(
    context: Context,
    private val deviceId: String,
) : Interceptor {

    companion object {
        const val CLIENT_VERSION = "1.260416.20"
        private const val MAX_ACCEPT_LANGUAGES = 3
    }

    private val packageName: String
    private val userAgent: String

    init {
        packageName = try {
            val pm = context.packageManager
            pm.getPackageInfo(context.packageName, 0).packageName
        } catch (e: PackageManager.NameNotFoundException) {
            "com.anthropic.claude"
        }

        userAgent = "Claude $packageName/$CLIENT_VERSION (Android ${Build.VERSION.SDK_INT})"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("User-Agent", userAgent)
            .header("Anthropic-Client-Platform", "android")
            .header("Anthropic-Client-App", packageName)
            .header("Anthropic-Client-Version", CLIENT_VERSION)
            .header("Anthropic-Client-OS-Version", Build.VERSION.SDK_INT.toString())
            .header("Anthropic-Device-ID", deviceId)
            .header("Accept-Language", buildAcceptLanguage())
            .build()

        return chain.proceed(request)
    }

    /**
     * Builds the Accept-Language header from the system's preferred locales.
     * Uses quality values (q=) descending by 0.1 for each additional locale.
     *
     * Example: `en-US, es-AR;q=0.9, pt-BR;q=0.8`
     */
    private fun buildAcceptLanguage(): String {
        val locales = mutableListOf<Locale>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = android.os.LocaleList.getDefault()
            val count = minOf(localeList.size(), MAX_ACCEPT_LANGUAGES)
            for (i in 0 until count) {
                locales.add(localeList[i])
            }
        } else {
            @Suppress("DEPRECATION")
            locales.add(Locale.getDefault())
        }

        if (locales.isEmpty()) return "en-US"

        return buildString {
            append(locales[0].toLanguageTag())
            var quality = 1.0f
            for (i in 1 until locales.size) {
                quality -= 0.1f
                append(", ")
                append(locales[i].toLanguageTag())
                append(";q=")
                append(quality)
            }
        }
    }
}
