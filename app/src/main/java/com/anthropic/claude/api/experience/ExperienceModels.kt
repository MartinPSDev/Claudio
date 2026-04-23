package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable

/**
 * A bullet point item shown inside an experience/onboarding card.
 * [icon] is an icon identifier; [text] is the display string.
 */
@Serializable
data class ExperienceBullet(
    val icon: String? = null,
    val text: String,
)

/**
 * A client-side action triggered by an experience card interaction.
 * The [type] field discriminates the action (e.g. "navigate", "dismiss").
 */
@Serializable
data class ExperienceClientAction(
    val type: String? = null,
    val payload: String? = null,
)

/**
 * Response carrying a list of client actions to execute after an experience event.
 */
@Serializable
data class ExperienceActionResponse(
    val actions: List<ExperienceClientAction> = emptyList(),
)
