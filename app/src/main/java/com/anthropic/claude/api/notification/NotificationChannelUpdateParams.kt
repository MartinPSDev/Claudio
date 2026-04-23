package com.anthropic.claude.api.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for registering or updating a push notification channel (FCM token).
 */
@Serializable
data class NotificationChannelUpdateParams(
    @SerialName("channel_type") val channelType: String,
    @SerialName("registration_token") val registrationToken: String,
    @SerialName("client_platform") val clientPlatform: String? = null,
    @SerialName("client_app_name") val clientAppName: String? = null,
    @SerialName("os_push_permission_granted") val osPushPermissionGranted: Boolean? = null,
)
