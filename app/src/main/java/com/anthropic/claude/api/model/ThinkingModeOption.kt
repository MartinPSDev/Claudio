package com.anthropic.claude.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A thinking mode option shown in the model/thinking mode picker.
 */
@Serializable
data class ThinkingModeOption(
    val id: String,
    val mode: String? = null,
    val title: String? = null,
    @SerialName("selection_title") val selectionTitle: String? = null,
    val description: String? = null,
    @SerialName("is_default") val isDefault: Boolean = false,
)
