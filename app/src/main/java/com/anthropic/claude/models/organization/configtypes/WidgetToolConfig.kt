package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Configuration for the home screen widget tool.
 */
@Serializable
data class WidgetToolConfig(
    val completion: JsonElement? = null,
)
