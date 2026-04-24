package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Output from the user-location tool — the device's current GPS position.
 */
@Serializable
data class UserLocationV0OutputUserLocationResult(
    val accuracy: Float? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val geocoded: JsonElement? = null,
)

/**
 * Input for requesting a structured form from the user.
 */
@Serializable
data class RequestFormInputFromUserInput(
    val domain: String? = null,
    val fields: List<JsonElement>? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
)

/**
 * Input for the message-compose tool (v1) — drafts a message with variants.
 */
@Serializable
data class MessageComposeV1Input(
    val kind: String? = null,
    @SerialName("summary_title") val summaryTitle: String? = null,
    val variants: List<JsonElement>? = null,
)
