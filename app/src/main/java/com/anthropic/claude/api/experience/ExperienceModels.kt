package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Sealed interface for experience content types.
 * Mapped from ExperienceContent.smali.
 */
@Serializable
@JsonClassDiscriminator("type")
sealed interface ExperienceContent

/**
 * Data for tracking experience actions.
 * Mapped from ExperienceTrackActionData.smali.
 */
@Serializable
data class ExperienceTrackActionData(
    val action: String? = null,
    val properties: Map<String, String>? = null
)

/**
 * Client-side experience action types.
 * Mapped from ExperienceClientAction.smali.
 */
@Serializable
data class ExperienceClientAction(
    val type: String,
    val data: ExperienceTrackActionData? = null
)
