package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Result of a user-location lookup (v0). */
@Serializable
data class UserLocationV0OutputUserLocationResult(
    val accuracy: Double? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val geocoded: JsonElement? = null,
)

/** Input for the request-form-input-from-user tool. */
@Serializable
data class RequestFormInputFromUserInput(
    val domain: String? = null,
    val fields: JsonElement? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
)

/** Input for the message-compose tool (v0). */
@Serializable
data class MessageComposeV0Input(
    val body: String? = null,
    val kind: String? = null,
    val subject: String? = null,
    @SerialName("summary_title") val summaryTitle: String? = null,
)

/** Input for the message-compose tool (v1). */
@Serializable
data class MessageComposeV1Input(
    val kind: String? = null,
    val variants: JsonElement? = null,
    @SerialName("summary_title") val summaryTitle: String? = null,
)

/** Input for the task-propose tool. */
@Serializable
data class TaskProposeInput(
    val context: String? = null,
    val title: String? = null,
    val steps: JsonElement? = null,
)

/** Result from a HealthConnect query (v0). */
@Serializable
data class HealthConnectQueryV0OutputHealthConnectQueryResult(
    val message: String? = null,
    @SerialName("query_results") val queryResults: JsonElement? = null,
)
