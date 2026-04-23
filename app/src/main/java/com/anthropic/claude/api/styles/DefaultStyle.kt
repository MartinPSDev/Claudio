package com.anthropic.claude.api.styles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A built-in system writing style (non-customizable).
 */
@Serializable
data class DefaultStyle(
    val name: String? = null,
    val key: String? = null,
    @SerialName("nameKey") val nameKey: String? = null,
    val summary: String? = null,
    @SerialName("summaryKey") val summaryKey: String? = null,
    val prompt: String? = null,
    @SerialName("isDefault") val isDefault: Boolean = false,
)
