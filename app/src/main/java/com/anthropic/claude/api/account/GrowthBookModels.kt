package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A GrowthBook feature definition with a default value and optional rules.
 */
@Serializable
data class GrowthBookFeature(
    @SerialName("defaultValue") val defaultValue: JsonElement? = null,
    val rules: List<GrowthBookRule>? = null,
)

/**
 * A single GrowthBook evaluation rule (force, experiment, or rollout).
 */
@Serializable
data class GrowthBookRule(
    val force: JsonElement? = null,
    val id: String? = null,
    val tracks: JsonElement? = null,
)

/**
 * The result of evaluating a GrowthBook experiment for the current user.
 */
@Serializable
data class GrowthBookExperimentResult(
    val inExperiment: Boolean = false,
    val variationId: Int? = null,
    val value: JsonElement? = null,
    val hashUsed: Boolean = false,
)
