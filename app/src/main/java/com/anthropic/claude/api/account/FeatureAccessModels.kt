package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Per-feature access control entry for a user.
 */
@Serializable
data class FeatureAccess(
    val feature: String? = null,
    val status: String? = null,
)

/**
 * Request to update orbit (briefings) settings for a user.
 */
@Serializable
data class UpdateOrbitSettingsRequest(
    val enabled: Boolean? = null,
    val timezone: String? = null,
)
