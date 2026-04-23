package com.anthropic.claude.api.feedback

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Device/environment info sent alongside in-app feedback submissions.
 */
@Serializable
data class DeviceInfo(
    @SerialName("os_version") val osVersion: String? = null,
    @SerialName("app_version") val appVersion: String? = null,
    @SerialName("device_model") val deviceModel: String? = null,
    @SerialName("screen_resolution") val screenResolution: String? = null,
    @SerialName("locale") val locale: String? = null,
    @SerialName("timezone") val timezone: String? = null,
)

/**
 * Response from the app feedback submission endpoint.
 */
@Serializable
data class AppFeedbackResponse(
    val success: Boolean = false,
    @SerialName("feedback_id") val feedbackId: String? = null,
)
