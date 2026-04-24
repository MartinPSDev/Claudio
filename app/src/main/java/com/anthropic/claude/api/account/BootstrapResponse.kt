package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Full response from the app-start / bootstrap endpoint.
 * Provides the account, organization, feature flags and GrowthBook data
 * needed to initialize the app without additional API calls.
 */
@Serializable
data class BootstrapResponse(
    val account: JsonElement? = null,
    val organization: JsonElement? = null,
    val growthbook: JsonElement? = null,
    @SerialName("feature_flags") val featureFlags: JsonElement? = null,
    @SerialName("mobile_config") val mobileConfig: JsonElement? = null,
)
