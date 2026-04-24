package com.anthropic.claude.api.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Full notification preferences for a user. */
@Serializable
data class Preferences(
    @SerialName("feature_preference") val featurePreference: JsonElement? = null,
)

/** Upsert (create-or-replace) notification preferences request. */
@Serializable
data class NotificationPreferencesUpsertParams(
    val preferences: JsonElement? = null,
)

/** Partial-update notification preferences request. */
@Serializable
data class NotificationPreferencesUpdateParams(
    val preferences: JsonElement? = null,
)
