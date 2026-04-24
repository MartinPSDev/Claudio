package com.anthropic.claude.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Signals that the user is approaching their rate limit.
 * Shown as a warning in the UI before the limit is reached.
 */
@Serializable
data class ApproachingRateLimit(
    @SerialName("resetsAt") val resetsAt: String? = null,
    val remaining: Int? = null,
    val message: String? = null,
    @SerialName("perModelLimit") val perModelLimit: Boolean = false,
)
