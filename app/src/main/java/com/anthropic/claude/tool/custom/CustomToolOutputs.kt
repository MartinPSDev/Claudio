package com.anthropic.claude.tool.custom

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Output of the recipe-display custom tool.
 */
@Serializable
data class RecipeDisplayOutput(
    val images: List<JsonElement>? = null,
)

/**
 * Output of the image-search custom tool.
 */
@Serializable
data class ImageSearchOutput(
    val images: List<JsonElement>? = null,
)
