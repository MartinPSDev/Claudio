package com.anthropic.claude.tool.custom

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Output of the image-search tool — a list of image results. */
@Serializable
data class ImageSearchOutput(val images: JsonElement? = null)

/** Output of the recipe-display tool — a list of rendered recipe images. */
@Serializable
data class RecipeDisplayOutput(val images: JsonElement? = null)
