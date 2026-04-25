package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to perform an experience action.
 */
@Serializable
data class ExperienceActionRequest(
    val experience_id: String? = null,
    val action: String? = null,
    val data: JsonElement? = null,
    val client_action: ExperienceClientAction? = null
)

/**
 * Client-side action for an experience.
 */
@Serializable
data class ExperienceClientAction(
    val type: String? = null,
    val payload: JsonElement? = null
)

/**
 * Rules that govern when an experience is shown.
 */
@Serializable
data class ExperienceRules(
    val min_app_version: String? = null,
    val max_app_version: String? = null,
    val platforms: List<String>? = null,
    val locales: List<String>? = null,
    val segments: List<String>? = null,
    val max_impressions: Int? = null,
    val cooldown_hours: Int? = null
)

/**
 * Data sent when tracking that an experience was actioned.
 */
@Serializable
data class TrackActionedData(
    val experience_id: String? = null,
    val action_type: String? = null,
    val action_value: String? = null,
    val timestamp: Long? = null
)

/**
 * Data sent when tracking that an experience was dismissed.
 */
@Serializable
data class TrackDismissedData(
    val experience_id: String? = null,
    val dismiss_reason: String? = null,
    val timestamp: Long? = null
)

/**
 * Data sent when tracking that an experience was shown.
 */
@Serializable
data class TrackShownData(
    val experience_id: String? = null,
    val placement: String? = null,
    val timestamp: Long? = null
)

/**
 * Wrapper for track action data.
 */
@Serializable
data class ExperienceTrackActionData(
    val type: String? = null,
    val data: JsonElement? = null
)
