package com.anthropic.claude.api.events

import kotlinx.serialization.Serializable

/**
 * Request to log a single analytics event.
 * Mapped from EventLoggingRequest.smali.
 */
@Serializable
data class EventLoggingRequest(
    val event_name: String,
    val properties: Map<String, String>? = null
)

/**
 * Request to log a batch of analytics events.
 * Mapped from BatchEventLoggingRequest.smali.
 */
@Serializable
data class BatchEventLoggingRequest(
    val events: List<EventLoggingRequest>
)

/**
 * Authentication info attached to analytics events.
 * Mapped from EventAuth.smali.
 */
@Serializable
data class EventAuth(
    val account_uuid: String? = null,
    val organization_uuid: String? = null,
    val session_id: String? = null
)

/**
 * Metadata about an experiment for analytics tracking.
 * Mapped from ExperimentMetadata.smali.
 */
@Serializable
data class ExperimentMetadata(
    val experiment_key: String? = null,
    val variation_id: Int? = null,
    val in_experiment: Boolean? = null
)
