package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Response body from tracking an actioned experience.
 */
@Serializable
data class TrackActionedData(
    val actions: List<JsonElement>? = null,
    val success: Boolean = false,
)

/**
 * Request body for performing an action on an experience.
 */
@Serializable
data class ExperienceActionRequest(
    @SerialName("experience_id") val experienceId: String,
    @SerialName("action_id") val actionId: String? = null,
    val params: JsonElement? = null,
)
