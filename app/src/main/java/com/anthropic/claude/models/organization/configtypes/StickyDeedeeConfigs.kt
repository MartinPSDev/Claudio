package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Configuration for sticky (persistent) model selection in the UI.
 */
@Serializable
data class StickySelectionConfig(
    @SerialName("model_selector") val modelSelector: JsonElement? = null,
)

/**
 * Configuration for the Deedee voice/speech assistant mode.
 */
@Serializable
data class DeedeeConfig(
    @SerialName("speech_input") val speechInput: JsonElement? = null,
)
