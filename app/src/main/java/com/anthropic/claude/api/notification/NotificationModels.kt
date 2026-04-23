package com.anthropic.claude.api.notification

import kotlinx.serialization.Serializable

/**
 * Parameters for tracking a push notification open event.
 * Contains a signed payload from the FCM notification data.
 */
@Serializable
data class NotificationParams(
    val signedPayload: String,
)

/**
 * Preference toggle for a specific notification channel.
 */
@Serializable
data class NotificationPreferencesSchema(
    val enabled: Boolean = true,
    val channelId: String? = null,
)

/**
 * Request to send a test push notification.
 */
@Serializable
data class TrackPushOpenRequest(
    val signedPayload: String,
    val notificationId: String? = null,
)
