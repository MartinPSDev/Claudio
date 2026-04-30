package com.anthropic.claude.updates

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration for the flexible in-app update flow (Play Core).
 *
 * When the installed version is older than [minStalenessDays] and the user
 * has not dismissed the prompt within the last [dismissCooldownDays],
 * a flexible update banner is shown.
 */
@Serializable
data class FlexibleUpdateConfig(
    /** Whether the flexible update prompt is enabled. */
    val enabled: Boolean = false,
    /** Minimum number of days since the update was published before prompting. */
    @SerialName("min_staleness_days") val minStalenessDays: Int = 7,
    /** Number of days to wait before re-prompting after a dismiss. */
    @SerialName("dismiss_cooldown_days") val dismissCooldownDays: Int = 3,
)
