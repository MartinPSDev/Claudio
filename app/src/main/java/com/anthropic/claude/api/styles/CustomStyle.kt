package com.anthropic.claude.api.styles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A user-defined or system custom writing style.
 */
@Serializable
data class CustomStyle(
    val uuid: String? = null,
    val name: String? = null,
    @SerialName("nameKey") val nameKey: String? = null,
    val summary: String? = null,
    @SerialName("summaryKey") val summaryKey: String? = null,
    val prompt: String? = null,
    val attributes: JsonElement? = null,
    @SerialName("isDefault") val isDefault: Boolean = false,
)
