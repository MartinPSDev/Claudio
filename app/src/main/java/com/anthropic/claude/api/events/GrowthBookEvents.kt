package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Event data for a GrowthBook experiment exposure — logged when a user
 * is assigned to a variation in an A/B experiment.
 */
@Serializable
data class GrowthBookExperimentEventData(
    @SerialName("event_id") val eventId: String,
    @SerialName("experiment_id") val experimentId: String,
    @SerialName("variation_id") val variationId: String,
    val timestamp: Long? = null,
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("device_id") val deviceId: String? = null,
    val auth: String? = null,
    val environment: String? = null,
    @SerialName("experiment_metadata") val experimentMetadata: JsonElement? = null,
    @SerialName("user_attributes") val userAttributes: JsonElement? = null,
)

/**
 * Event data for a GrowthBook feature evaluation — logged each time
 * a feature flag is evaluated for a user.
 */
@Serializable
data class GrowthBookFeatureEvaluationEventData(
    @SerialName("event_id") val eventId: String,
    @SerialName("feature_id") val featureId: String? = null,
    val timestamp: Long? = null,
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("device_id") val deviceId: String? = null,
    val auth: String? = null,
    val environment: String? = null,
    @SerialName("user_attributes") val userAttributes: JsonElement? = null,
)
