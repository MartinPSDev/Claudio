package com.anthropic.claude.wear

import com.google.android.gms.wearable.WearableListenerService
import kotlinx.serialization.json.Json

class PhoneWearableListenerService : WearableListenerService() {

    private val json = Json { ignoreUnknownKeys = true }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun resolveCredentials(): AuthSyncCredentials? {
        val prefs = applicationContext.getSharedPreferences("app_prefs", 0)
        val accountId = prefs.getString("session_key", null) ?: return null

        val accountPrefs = applicationContext
            .getSharedPreferences("account_prefs$accountId", 0)

        val orgId = accountPrefs.getString("selected_org_id", null) ?: return null

        val sessionKey = findSessionKeyInCookies(accountId) ?: return null

        val baseUrl = resolveBaseUrl()

        val playbackSpeed = when (accountPrefs.getString("bell_playback_speed", null)) {
            "SLOW" -> 0.8f
            "FAST" -> 1.2f
            else -> 1.0f
        }

        return AuthSyncCredentials(
            sessionKey = sessionKey,
            orgId = orgId,
            baseUrl = baseUrl,
            voiceSelection = accountPrefs.getString("voice_selection", null),
            playbackSpeed = playbackSpeed,
        )
    }

    private fun findSessionKeyInCookies(accountId: String): String? {
        val cookiePrefs = applicationContext
            .getSharedPreferences("user_cookies_$accountId", 0)

        return cookiePrefs.all.entries
            .filter { it.key.endsWith("|sessionKey") && it.value is String }
            .firstNotNullOfOrNull { entry ->
                try {
                    val cookie = json.decodeFromString<SerializableCookieSlim>(entry.value as String)
                    cookie.value.takeIf { cookie.name == "sessionKey" && it.isNotEmpty() }
                } catch (e: Exception) {
                    null
                }
            }
    }

    private fun resolveBaseUrl(): String {
        val debugPrefs = applicationContext.getSharedPreferences("app_prefs", 0)
        val customHost = debugPrefs.getString("debug_host", null)
        return when {
            customHost?.contains("localhost") == true -> customHost
            customHost?.contains("10.0.2.2") == true -> customHost
            customHost?.contains("127.0.0.1") == true -> customHost
            else -> "https://claude.ai"
        }
    }

    companion object {
        private const val WEAR_PATH_PREFIX = "/claude"
    }
}
