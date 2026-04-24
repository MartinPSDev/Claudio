package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rate-limiting and cooldown rules for an experience.
 */
@Serializable
data class ExperienceRuleSet(
    @SerialName("rate_limit") val rateLimit: Int? = null,
    val cooldown: Int? = null,
)
