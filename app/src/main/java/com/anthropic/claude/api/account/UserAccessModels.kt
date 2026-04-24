package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Current user's access rights — which features are available to them.
 */
@Serializable
data class CurrentUserAccess(
    val features: JsonElement? = null,
)

/**
 * A GrowthBook experiment tracking payload sent to the analytics pipeline.
 */
@Serializable
data class GrowthBookTrack(
    val experiment: String? = null,
    val result: String? = null,
)
