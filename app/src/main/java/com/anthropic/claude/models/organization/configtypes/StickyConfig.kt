package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration controlling caching / sticky behavior for org-level settings.
 */
@Serializable
data class StickyConfig(
    val enabled: Boolean = false,
    @SerialName("ttl_seconds") val ttlSeconds: Int = 0,
)
