package com.anthropic.claude.api.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Current notification preferences keyed by feature. */
@Serializable
data class Preferences(
    @SerialName("feature_preference") val featurePreference: JsonElement? = null,
)

/** Params to upsert all notification preferences. */
@Serializable
data class NotificationPreferencesUpsertParams(
    val preferences: Preferences? = null,
)

/** Params to update specific notification preferences. */
@Serializable
data class NotificationPreferencesUpdateParams(
    val preferences: Preferences? = null,
)
