package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration for which beta tools are enabled for the organization.
 */
@Serializable
data class BetaToolsConfig(
    @SerialName("beta_tools") val betaTools: List<String>? = null,
)
