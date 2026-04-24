package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for tracking an interaction with an experience (impression, click, dismiss).
 */
@Serializable
data class ExperienceTrackRequest(
    @SerialName("experience_id") val experienceId: String,
    val action: String? = null,
    val metadata: JsonElement? = null,
)
