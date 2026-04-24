package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Event logging request for a feature evaluation (GrowthBook flag check).
 */
@Serializable
data class FeatureEvaluationEvent(
    @SerialName("event_data") val eventData: JsonElement? = null,
)

/**
 * Event logging request for an experiment exposure (A/B test enrollment).
 */
@Serializable
data class ExperimentExposureEvent(
    @SerialName("event_data") val eventData: JsonElement? = null,
)
