package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A banner content card served by the experience API.
 * Displayed as a dismissible or persistent in-app banner.
 */
@Serializable
data class BannerContent(
    val title: String? = null,
    val description: String? = null,
    val asset: JsonElement? = null,
    val buttons: List<JsonElement>? = null,
    @SerialName("inlineButtons") val inlineButtons: List<JsonElement>? = null,
)
