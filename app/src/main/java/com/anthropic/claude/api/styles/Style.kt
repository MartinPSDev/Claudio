package com.anthropic.claude.api.styles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** A conversation style (system prompt preset). */
@Serializable
data class Style(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    @SerialName("prompt_template") val promptTemplate: String? = null,
    @SerialName("is_default")      val isDefault: Boolean? = null,
    val icon: JsonElement? = null,
)
