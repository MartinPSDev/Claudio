package com.anthropic.claude.api.notification

import kotlinx.serialization.Serializable

/**
 * Notification preferences wrapper.
 */
@Serializable
data class Preferences(
    val feature_preference: FeaturePreference? = null
)

/**
 * Per-feature notification preference.
 */
@Serializable
data class FeaturePreference(
    val enabled: Boolean = true,
    val channels: List<String>? = null
)

/**
 * Request to update notification preferences.
 */
@Serializable
data class NotificationPreferencesUpdateParams(
    val preferences: Preferences? = null,
    val device_token: String? = null,
    val platform: String? = null
)

/**
 * Request to upsert notification preferences.
 */
@Serializable
data class NotificationPreferencesUpsertParams(
    val preferences: Preferences? = null,
    val device_token: String? = null,
    val platform: String? = null
)
