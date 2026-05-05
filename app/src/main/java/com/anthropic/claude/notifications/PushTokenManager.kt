package com.anthropic.claude.notifications

import android.util.Log
import com.anthropic.claude.networking.AnthropicApiClient
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

/**
 * Manages FCM push token registration with the Anthropic backend.
 *
 * On login or token refresh:
 * 1. Retrieves the FCM token from FirebaseMessaging
 * 2. POSTs it to `/api/push_notification/register` with the device ID
 * 3. Stores registration state to avoid redundant calls
 *
 * On logout:
 * 1. Calls `/api/push_notification/unregister`
 * 2. Clears local registration state
 */
class PushTokenManager(
    private val apiClient: AnthropicApiClient,
    private val prefsProvider: () -> android.content.SharedPreferences,
) {
    companion object {
        private const val TAG = "PushTokenManager"
        private const val KEY_REGISTERED_TOKEN = "push_registered_token"
        private const val KEY_REGISTERED_ACCOUNT = "push_registered_account"
    }

    /**
     * Registers the current FCM token with the Anthropic API.
     * No-ops if the token has already been registered for this account.
     *
     * @param orgId Organization UUID.
     * @param accountId Account UUID.
     * @param deviceId The unique device ID.
     */
    suspend fun registerIfNeeded(orgId: String, accountId: String, deviceId: String) {
        try {
            val token = FirebaseMessaging.getInstance().token.await()

            val prefs = prefsProvider()
            val registeredToken = prefs.getString(KEY_REGISTERED_TOKEN, null)
            val registeredAccount = prefs.getString(KEY_REGISTERED_ACCOUNT, null)

            if (token == registeredToken && accountId == registeredAccount) {
                Log.d(TAG, "Token already registered for account $accountId")
                return
            }

            apiClient.registerPushToken(orgId, token, deviceId)

            prefs.edit()
                .putString(KEY_REGISTERED_TOKEN, token)
                .putString(KEY_REGISTERED_ACCOUNT, accountId)
                .apply()

            Log.i(TAG, "Push token registered successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to register push token", e)
        }
    }

    /**
     * Unregisters the current device from push notifications.
     */
    suspend fun unregister(orgId: String, deviceId: String) {
        try {
            apiClient.unregisterPushToken(orgId, deviceId)

            prefsProvider().edit()
                .remove(KEY_REGISTERED_TOKEN)
                .remove(KEY_REGISTERED_ACCOUNT)
                .apply()

            Log.i(TAG, "Push token unregistered")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to unregister push token", e)
        }
    }

    /**
     * Called when FCM issues a new token (token refresh).
     * Re-registers with the backend using the new token.
     */
    suspend fun onTokenRefresh(orgId: String, accountId: String, deviceId: String) {
        prefsProvider().edit()
            .remove(KEY_REGISTERED_TOKEN)
            .apply()

        registerIfNeeded(orgId, accountId, deviceId)
    }
}
