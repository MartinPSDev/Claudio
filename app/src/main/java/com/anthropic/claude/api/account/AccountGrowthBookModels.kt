package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Current user's feature access map. */
@Serializable
data class CurrentUserAccess(
    val features: JsonElement? = null,
)

/** Access status for a single feature for the current user. */
@Serializable
data class FeatureAccess(
    val feature: String? = null,
    val status: String? = null,
)

/** A single GrowthBook experiment (key + rules). */
@Serializable
data class GrowthBookExperiment(
    val key: String? = null,
    val variations: JsonElement? = null,
    val weights: JsonElement? = null,
)

/** Result of a GrowthBook experiment evaluation. */
@Serializable
data class GrowthBookExperimentResult(
    @SerialName("inExperiment") val inExperiment: Boolean? = null,
    @SerialName("variationId")  val variationId: Int? = null,
    val value: JsonElement? = null,
    @SerialName("hashUsed")     val hashUsed: Boolean? = null,
)

/** A GrowthBook feature definition (defaultValue + rules). */
@Serializable
data class GrowthBookFeature(
    val defaultValue: JsonElement? = null,
    val rules: JsonElement? = null,
    val experiment: JsonElement? = null,
    val force: JsonElement? = null,
)

/** A single GrowthBook targeting rule. */
@Serializable
data class GrowthBookRule(
    val force: JsonElement? = null,
    val id: String? = null,
    val tracks: JsonElement? = null,
)

/** A GrowthBook experiment track record. */
@Serializable
data class GrowthBookTrack(
    val experiment: JsonElement? = null,
    val result: JsonElement? = null,
)

/** Request to update Orbit assistant settings. */
@Serializable
data class UpdateOrbitSettingsRequest(
    val enabled: Boolean? = null,
    val timezone: String? = null,
)
