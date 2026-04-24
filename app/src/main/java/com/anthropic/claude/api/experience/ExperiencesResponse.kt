package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Placement rules that control when and where experiences are shown.
 */
@Serializable
data class ExperienceRules(
    val global: JsonElement? = null,
    val placements: JsonElement? = null,
)

/**
 * Full response from the experiences API endpoint.
 */
@Serializable
data class ExperiencesResponse(
    val experiences: List<Experience>? = null,
    val rules: ExperienceRules? = null,
)
