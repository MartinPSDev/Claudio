package com.anthropic.claude.firebase

import android.content.Context
import android.util.Log
import com.anthropic.claude.analytics.events.PushEvents
import com.anthropic.claude.api.notification.ClientPlatform
import com.anthropic.claude.api.notification.NotificationChannelType
import com.anthropic.claude.api.notification.NotificationChannelUpdateParams
import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.networking.AnthropicApiClient

/**
 * Registers/unregisters the FCM push token with the Anthropic backend.
 *
 * On success, fires [PushEvents.PushRegistrationSuccess]. On failure,
 * fires [PushEvents.PushRegistrationFailure] with the appropriate
 * [PushEvents.FailureCause] (FCM_TOKEN_ERROR, NETWORK_ERROR).
 */
class PushNotificationRegistrar(
    private val context: Context,
    private val apiClient: AnthropicApiClient,
    private val analyticsTracker: AnalyticsTracker,
) {
    companion object {
        private const val TAG = "PushNotificationRegistrar"
        private const val PACKAGE_NAME = "com.anthropic.claude"
    }

    /**
     * Sends the FCM token to the Anthropic API for this organization.
     *
     * @param orgId The current organization UUID.
     * @param fcmToken The FCM registration token.
     */
    suspend fun registerToken(orgId: String, fcmToken: String) {
        val params = NotificationChannelUpdateParams(
            channelType = NotificationChannelType.FCM,
            packageName = PACKAGE_NAME,
            clientPlatform = ClientPlatform.ANDROID,
            token = fcmToken,
            notificationsEnabled = areNotificationsEnabled(),
        )

        when (val result = apiClient.registerPushToken(orgId, params)) {
            is ApiResult.Success -> {
                Log.i(TAG, "Push token registered successfully for org=$orgId")
                analyticsTracker.track(
                    PushEvents.PushRegistrationSuccess(orgId)
                )
            }

            is ApiResult.Error -> {
                Log.e(TAG, "Push registration failed: ${result.statusCode} ${result.message}")
                analyticsTracker.track(
                    PushEvents.PushRegistrationFailure(
                        cause = PushEvents.FailureCause.NETWORK_ERROR,
                        statusCode = result.statusCode,
                        message = result.message,
                    )
                )
            }

            is ApiResult.NetworkError -> {
                Log.e(TAG, "Push registration network error", result.exception)
                analyticsTracker.track(
                    PushEvents.PushRegistrationFailure(
                        cause = PushEvents.FailureCause.NETWORK_ERROR,
                        statusCode = null,
                        message = result.exception.message,
                    )
                )
            }
        }
    }

    /**
     * Checks whether the app has notification permission at the OS level.
     */
    private fun areNotificationsEnabled(): Boolean {
        return try {
            val manager = context.getSystemService(android.app.NotificationManager::class.java)
            manager?.areNotificationsEnabled() == true
        } catch (_: Exception) {
            false
        }
    }
}
