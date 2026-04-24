package com.anthropic.claude.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rate-limit state — within allowed usage.
 */
@Serializable
data class RateLimitWithinLimit(
    @SerialName("within_limit") val withinLimit: Boolean = true,
)
