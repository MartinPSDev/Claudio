package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Detailed event data for a GrowthBook A/B experiment enrollment. */
@Serializable
data class GrowthBookExperimentEventData(
    @SerialName("experiment_id")       val experimentId: String? = null,
    @SerialName("experiment_metadata") val experimentMetadata: JsonElement? = null,
    @SerialName("session_id")          val sessionId: String? = null,
    @SerialName("device_id")           val deviceId: String? = null,
    @SerialName("user_attributes")     val userAttributes: JsonElement? = null,
    @SerialName("environment")         val environment: String? = null,
    @SerialName("timestamp")           val timestamp: String? = null,
    val auth: JsonElement? = null,
)

/** Detailed event data for a GrowthBook feature flag evaluation. */
@Serializable
data class GrowthBookFeatureEvaluationEventData(
    @SerialName("feature_key")     val featureKey: String? = null,
    @SerialName("rule_id")         val ruleId: String? = null,
    @SerialName("project_id")      val projectId: String? = null,
    @SerialName("unit_id")         val unitId: String? = null,
    @SerialName("reason")          val reason: String? = null,
    @SerialName("user_attributes") val userAttributes: JsonElement? = null,
    @SerialName("environment")     val environment: String? = null,
    @SerialName("timestamp")       val timestamp: String? = null,
)
