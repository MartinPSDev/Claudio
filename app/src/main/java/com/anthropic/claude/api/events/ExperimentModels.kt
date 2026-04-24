package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Metadata for a GrowthBook A/B experiment. */
@Serializable
data class ExperimentMetadata(
    @SerialName("feature_id") val featureId: String? = null,
)

/** A GrowthBook experiment definition. */
@Serializable
data class GrowthBookExperiment(
    val key: String? = null,
)
