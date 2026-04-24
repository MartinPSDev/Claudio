package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request to subscribe to PR status events in a remote code session.
 */
@Serializable
data class PrSubscriptionRequest(
    @SerialName("session_id") val sessionId: String? = null,
    val repo: String? = null,
    @SerialName("pr_number") val prNumber: Int? = null,
)
