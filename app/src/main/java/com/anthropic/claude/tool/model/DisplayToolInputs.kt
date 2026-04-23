package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Input for the places/map display tool — renders an interactive map with locations.
 */
@Serializable
data class PlacesMapDisplayV0Input(
    val days: JsonElement? = null,
    val locations: List<JsonElement>? = null,
    val title: String? = null,
    val narrative: String? = null,
    val mode: String? = null,
    @SerialName("show_route") val showRoute: Boolean = false,
    @SerialName("travel_mode") val travelMode: String? = null,
)

/**
 * Input for the recipe display tool — renders a structured recipe card.
 */
@Serializable
data class RecipeDisplayV0Input(
    @SerialName("base_servings") val baseServings: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val ingredients: List<JsonElement>? = null,
    val steps: List<JsonElement>? = null,
    val notes: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("image_page_url") val imagePageUrl: String? = null,
    @SerialName("image_source") val imageSource: String? = null,
)

/**
 * Input for the chart display tool — renders a data chart.
 */
@Serializable
data class ChartDisplayV0Input(
    val series: List<JsonElement>? = null,
    val title: String? = null,
    val style: String? = null,
    @SerialName("x_axis") val xAxis: JsonElement? = null,
    @SerialName("y_axis") val yAxis: JsonElement? = null,
)

/**
 * Input for creating a calendar event via the app-use tool.
 */
@Serializable
data class EventCreateV0Input(
    @SerialName("all_day") val allDay: Boolean = false,
    val title: String? = null,
    val description: String? = null,
    val location: String? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time") val endTime: String? = null,
    val recurrence: JsonElement? = null,
)
