package com.anthropic.claude.networking.cookies

import android.content.Context
import android.content.SharedPreferences

/**
 * Manages HTTP cookie persistence for user sessions.
 *
 * Cookie keys follow the pattern:
 *   - `user_cookies_<accountId>` — for a specific account
 *   - `user_cookies_login` — for the login-phase cookies (no account yet)
 *   - `user_cookies___<domain>__<accountId>` — for domain-specific cookies
 *   - `user_cookies_login__<domain>` — for domain-specific login cookies
 *
 * The critical cookie is `sessionKey`, which is the authentication token
 * for all API calls. It's extracted from the cookie jar to be injected
 * into the `SessionInterceptor` header.
 */
object CookieStore {

    private const val SESSION_COOKIE_NAME = "sessionKey"

    /**
     * Returns the preference file name for storing cookies.
     *
     * @param domain The API domain (e.g., "claude.ai"). Null uses default key.
     * @param accountId The user's account UUID. Null uses login-phase key.
     */
    fun prefsKey(domain: String?, accountId: String?): String {
        return when {
            domain == null && accountId != null -> "user_cookies_$accountId"
            domain == null && accountId == null -> "user_cookies_login"
            domain != null && accountId != null -> {
                val safeDomain = domain.replace("/", "-")
                "user_cookies___${safeDomain}__$accountId"
            }
            else -> {
                val safeDomain = domain!!.replace("/", "-")
                "user_cookies_login__$safeDomain"
            }
        }
    }

    /**
     * Extracts the `sessionKey` cookie value from the cookie store.
     *
     * @param context Application context.
     * @param domain The API domain.
     * @param accountId The user's account UUID.
     * @return The session key string, or null if not found or expired.
     */
    fun getSessionKey(context: Context, domain: String?, accountId: String?): String? {
        val key = prefsKey(domain, accountId)
        val prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val allCookies = prefs.all

        for ((name, value) in allCookies) {
            if (name == SESSION_COOKIE_NAME && value is String) {
                return value
            }
        }

        return null
    }

    /**
     * Saves a cookie to the appropriate SharedPreferences file.
     */
    fun saveCookie(
        context: Context,
        domain: String?,
        accountId: String?,
        cookieName: String,
        cookieValue: String,
    ) {
        val key = prefsKey(domain, accountId)
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
            .edit()
            .putString(cookieName, cookieValue)
            .apply()
    }

    /**
     * Migrates cookies when a user transitions from login-phase to authenticated.
     * Copies all cookies from the old preferences file to the new one,
     * then deletes the old file.
     *
     * @param context Application context.
     * @param oldDomain Previous domain key.
     * @param newDomain New domain key.
     * @param accountId The now-authenticated account UUID.
     */
    fun migrateCookies(
        context: Context,
        oldDomain: String?,
        newDomain: String?,
        accountId: String?,
    ) {
        val oldKey = prefsKey(oldDomain, accountId = null)
        val newKey = prefsKey(newDomain, accountId)

        if (oldKey == newKey) return

        val oldPrefs = context.getSharedPreferences(oldKey, Context.MODE_PRIVATE)
        val newPrefs = context.getSharedPreferences(newKey, Context.MODE_PRIVATE)

        val editor = newPrefs.edit()
        for ((k, v) in oldPrefs.all) {
            when (v) {
                is String -> editor.putString(k, v)
                is Long -> editor.putLong(k, v)
                is Int -> editor.putInt(k, v)
                is Boolean -> editor.putBoolean(k, v)
            }
        }
        editor.apply()

        context.deleteSharedPreferences(oldKey)
    }

    /**
     * Clears all cookies for the given account.
     */
    fun clearCookies(context: Context, domain: String?, accountId: String?) {
        val key = prefsKey(domain, accountId)
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}
