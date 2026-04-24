package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rate-limit state for an experience — how many times it can still be shown.
 */
@Serializable
data class ExperienceRateLimit(
    val remaining: Int? = null,
    @SerialName("reset_at") val resetAt: String? = null,
)
