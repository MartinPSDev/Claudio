package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * An account's membership in an organization, including role and notification preferences.
 */
@Serializable
data class Membership(
    val organization: JsonElement? = null,
    val role: String? = null,
    @SerialName("notification_preferences") val notificationPreferences: JsonElement? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
)
