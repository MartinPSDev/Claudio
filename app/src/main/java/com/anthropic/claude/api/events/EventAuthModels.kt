package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Authentication context attached to a batch event log entry.
 */
@Serializable
data class EventAuth(
    @SerialName("account_uuid") val accountUuid: String? = null,
    @SerialName("organization_uuid") val organizationUuid: String? = null,
)

/**
 * Request body for the batch event logging endpoint.
 */
@Serializable
data class BatchEventLoggingRequest(
    val events: List<JsonElement>? = null,
)
