package com.anthropic.claude.api.events

import kotlinx.serialization.Serializable

/**
 * Authentication data sent with analytics events.
 */
@Serializable
data class EventAuth(
    val account_uuid: String? = null,
    val organization_uuid: String? = null
)

/**
 * Batch request containing multiple event logging requests.
 */
@Serializable
data class BatchEventLoggingRequest(
    val events: List<EventLoggingRequest>
)

/**
 * A/B experiment metadata attached to events.
 */
@Serializable
data class ExperimentMetadata(
    val feature_id: String
)
