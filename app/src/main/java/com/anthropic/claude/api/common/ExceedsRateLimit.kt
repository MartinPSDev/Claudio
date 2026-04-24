package com.anthropic.claude.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Signals that the user has exceeded their rate limit.
 * Shown as a hard block in the UI until the limit resets.
 */
@Serializable
data class ExceedsRateLimit(
    @SerialName("resetsAt") val resetsAt: String? = null,
    val message: String? = null,
    @SerialName("perModelLimit") val perModelLimit: Boolean = false,
)
