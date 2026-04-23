package com.anthropic.claude.api.usage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A single usage window showing current utilization and when it resets.
 */
@Serializable
data class UsageWindow(
    @SerialName("resets_at") val resetsAt: String? = null,
    val utilization: Double? = null,
)

/**
 * Full usage response aggregating multiple windows (daily, monthly, etc.).
 */
@Serializable
data class UsageResponse(
    @SerialName("daily") val daily: UsageWindow? = null,
    @SerialName("monthly") val monthly: UsageWindow? = null,
    @SerialName("total_tokens_used") val totalTokensUsed: Long? = null,
    @SerialName("total_tokens_limit") val totalTokensLimit: Long? = null,
    @SerialName("tokens_remaining") val tokensRemaining: Long? = null,
)
