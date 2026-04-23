package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A spotlight/promotional content card served by the experience API.
 * Displayed as an in-app announcement, feature highlight, or onboarding prompt.
 */
@Serializable
data class SpotlightContent(
    val title: String? = null,
    val description: String? = null,
    @SerialName("badge_title") val badgeTitle: String? = null,
    val asset: JsonElement? = null,
    val bullets: List<JsonElement>? = null,
    val buttons: List<JsonElement>? = null,
    val dismissible: Boolean = true,
)
