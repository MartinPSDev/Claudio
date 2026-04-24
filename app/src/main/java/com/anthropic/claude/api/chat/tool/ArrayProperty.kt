package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A JSON Schema array-type property for tool input definitions.
 */
@Serializable
data class ArrayProperty(
    val description: String? = null,
    val items: String? = null,
    @SerialName("minItems") val minItems: Int? = null,
    @SerialName("maxItems") val maxItems: Int? = null,
)
