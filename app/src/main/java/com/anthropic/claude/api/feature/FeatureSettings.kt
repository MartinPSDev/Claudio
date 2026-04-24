package com.anthropic.claude.api.feature

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Organization-level feature settings — lists disabled features and integrations.
 */
@Serializable
data class FeatureSettings(
    @SerialName("disabled_features") val disabledFeatures: List<String>? = null,
    @SerialName("disabled_integrations") val disabledIntegrations: List<String>? = null,
)
