package com.anthropic.claude.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration for the in-app flexible update flow.
 * Controls when and how often the app prompts the user to update.
 */
@Serializable
data class FlexibleUpdateConfig(
    val enabled: Boolean = false,
    @SerialName("min_staleness_days") val minStalenessDays: Int = 7,
    @SerialName("dismiss_cooldown_days") val dismissCooldownDays: Int = 3,
)
