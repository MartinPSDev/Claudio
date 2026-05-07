package com.anthropic.claude.sessions

import android.content.Context
import android.util.Log
import com.anthropic.claude.datastore.InternalPreferencesStore
import com.anthropic.claude.networking.cookies.CookieStore
import java.util.UUID

/**
 * Manages the user's active session lifecycle.
 *
 * Responsibilities:
 *   - Generate and persist a unique device ID
 *   - Track the active account ID and organization ID
 *   - Coordinate session key extraction from cookies
 *   - Clear session state on logout
 *
 * The device ID is generated once on first launch and persisted
 * in [InternalPreferencesStore]. It is sent in the `X-Device-Id` header.
 */
class SessionManager(
    private val context: Context,
    private val preferencesStore: InternalPreferencesStore,
) {
    companion object {
        private const val TAG = "SessionManager"
        private const val KEY_DEVICE_ID = "device_id"
        private const val KEY_ACCOUNT_ID = "active_account_id"
        private const val KEY_ORG_ID = "selected_org_id"
        private const val KEY_BASE_URL = "api_base_url"
        private const val DEFAULT_BASE_URL = "https://claude.ai"
    }

    /**
     * Returns the persistent device ID, generating one if needed.
     */
    val deviceId: String
        get() {
            var id = preferencesStore.getString(KEY_DEVICE_ID)
            if (id == null) {
                id = UUID.randomUUID().toString()
                preferencesStore.putString(KEY_DEVICE_ID, id)
                Log.i(TAG, "Generated new device ID")
            }
            return id
        }

    /**
     * Returns the active account ID, or null if not logged in.
     */
    var accountId: String?
        get() = preferencesStore.getString(KEY_ACCOUNT_ID)
        set(value) {
            if (value != null) {
                preferencesStore.putString(KEY_ACCOUNT_ID, value)
            } else {
                preferencesStore.remove(KEY_ACCOUNT_ID)
            }
        }

    /**
     * Returns the selected organization ID.
     */
    var orgId: String?
        get() = preferencesStore.getString(KEY_ORG_ID)
        set(value) {
            if (value != null) {
                preferencesStore.putString(KEY_ORG_ID, value)
            } else {
                preferencesStore.remove(KEY_ORG_ID)
            }
        }

    /**
     * Returns the API base URL (production or custom).
     */
    var baseUrl: String
        get() = preferencesStore.getString(KEY_BASE_URL) ?: DEFAULT_BASE_URL
        set(value) = preferencesStore.putString(KEY_BASE_URL, value)

    /**
     * Returns the session key cookie for the current account.
     */
    fun getSessionKey(): String? {
        val account = accountId ?: return null
        return CookieStore.getSessionKey(context, domain = null, accountId = account)
    }

    /**
     * Returns true if the user has an active session.
     */
    val isLoggedIn: Boolean
        get() = accountId != null && getSessionKey() != null

    /**
     * Sets up a new session after login.
     */
    fun onLoginSuccess(accountId: String, orgId: String) {
        this.accountId = accountId
        this.orgId = orgId

        // Migrate cookies from login-phase to account-scoped
        CookieStore.migrateCookies(
            context = context,
            oldDomain = null,
            newDomain = null,
            accountId = accountId,
        )

        Log.i(TAG, "Session established for account $accountId")
    }

    /**
     * Clears all session state on logout.
     */
    fun logout() {
        val account = accountId
        if (account != null) {
            CookieStore.clearCookies(context, domain = null, accountId = account)
        }
        accountId = null
        orgId = null
        Log.i(TAG, "Session cleared")
    }
}
