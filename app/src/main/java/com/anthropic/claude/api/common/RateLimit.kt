package com.anthropic.claude.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sealed interface representing the user's current message rate-limit status.
 * Returned in the [messageLimit] field of chat completion responses.
 */
@Serializable
sealed interface RateLimit {

    /** Usage is within normal bounds — no warnings needed. */
    @Serializable
    @SerialName("within_limit")
    data object WithinLimit : RateLimit

    /** Usage is near the rate limit — warn the user. */
    @Serializable
    @SerialName("approaching_limit")
    data class ApproachingLimit(
        @SerialName("remaining") val remaining: Int? = null,
        @SerialName("reset_at") val resetAt: String? = null,
    ) : RateLimit

    /** Rate limit exceeded — further messages are blocked. */
    @Serializable
    @SerialName("exceeds_limit")
    data class ExceedsLimit(
        @SerialName("reset_at") val resetAt: String? = null,
    ) : RateLimit
}
