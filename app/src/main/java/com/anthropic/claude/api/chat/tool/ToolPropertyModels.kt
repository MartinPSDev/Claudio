package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * JSON Schema integer property definition for a tool's input schema.
 */
@Serializable
data class IntegerProperty(
    val description: String? = null,
    val minimum: Int? = null,
    val maximum: Int? = null,
)

/**
 * JSON Schema number (float) property definition for a tool's input schema.
 */
@Serializable
data class NumberProperty(
    val description: String? = null,
    val minimum: Double? = null,
    val maximum: Double? = null,
)

/**
 * Source metadata attached to a generic tool result.
 */
@Serializable
data class GenericSourceMetadata(
    @SerialName("preview_title") val previewTitle: String? = null,
    val source: String? = null,
    @SerialName("icon_url") val iconUrl: String? = null,
)
