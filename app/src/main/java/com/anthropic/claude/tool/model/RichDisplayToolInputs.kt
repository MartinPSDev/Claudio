package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Input for the recipe-display tool (v0). */
@Serializable
data class RecipeDisplayV0Input(
    val title: String? = null,
    val description: String? = null,
    val ingredients: JsonElement? = null,
    val steps: JsonElement? = null,
    val notes: String? = null,
    @SerialName("image_url")      val imageUrl: String? = null,
    @SerialName("image_source")   val imageSource: String? = null,
    @SerialName("image_page_url") val imagePageUrl: String? = null,
)

/** Input for the places-map-display tool (v0). */
@Serializable
data class PlacesMapDisplayV0Input(
    val days: JsonElement? = null,
    val locations: JsonElement? = null,
    val title: String? = null,
    val narrative: String? = null,
    val mode: String? = null,
    @SerialName("show_route")    val showRoute: Boolean? = null,
    @SerialName("travel_mode")   val travelMode: String? = null,
)

/** Input for the chart-display tool (v0). */
@Serializable
data class ChartDisplayV0Input(
    val series: JsonElement? = null,
    val title: String? = null,
    val style: String? = null,
    @SerialName("x_axis") val xAxis: JsonElement? = null,
    @SerialName("y_axis") val yAxis: JsonElement? = null,
)
