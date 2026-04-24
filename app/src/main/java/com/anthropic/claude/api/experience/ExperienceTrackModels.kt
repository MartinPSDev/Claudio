package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to record an actioned experience (CTA clicked, etc.). */
@Serializable
data class ExperienceActionRequest(
    @SerialName("experience_id") val experienceId: String? = null,
    @SerialName("action_id")     val actionId: String? = null,
    val params: JsonElement? = null,
)

/** Experience placement rules (global + per-placement). */
@Serializable
data class ExperienceRules(
    val global: JsonElement? = null,
    val placements: JsonElement? = null,
)

/** Data for a "track actioned" experience event. */
@Serializable
data class TrackActionedData(
    val actions: JsonElement? = null,
    val success: Boolean? = null,
)

/** Data for a "track dismissed" experience event. */
@Serializable
data class TrackDismissedData(val reason: String? = null)

/** Data for a "track shown" experience event. */
@Serializable
data class TrackShownData(val visible: Boolean? = null)
