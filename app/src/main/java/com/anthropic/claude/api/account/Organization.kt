package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Organization entity returned from the account API.
 */
@Serializable
data class Organization(
    val uuid: String,
    val name: String? = null,
    @SerialName("billing_type") val billingType: String? = null,
    @SerialName("rate_limit_tier") val rateLimitTier: String? = null,
    @SerialName("raven_type") val ravenType: String? = null,
    @SerialName("free_credits_status") val freeCreditsStatus: String? = null,
    val capabilities: List<String>? = null,
    val settings: JsonElement? = null,
    @SerialName("api_disabled_reason") val apiDisabledReason: String? = null,
    @SerialName("api_disabled_until") val apiDisabledUntil: String? = null,
    @SerialName("billable_usage_paused_until") val billableUsagePausedUntil: String? = null,
    @SerialName("claude_ai_bootstrap_models_config") val claudeAiBootstrapModelsConfig: JsonElement? = null,
)
