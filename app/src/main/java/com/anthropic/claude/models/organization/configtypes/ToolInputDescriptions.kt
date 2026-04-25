package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Tool descriptions for the alarm-create tool.
 */
@Serializable
data class AlarmCreateInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val days: String? = null,
    val hour: String? = null,
    val minute: String? = null,
    val message: String? = null,
    val vibrate: String? = null,
)

/**
 * Tool descriptions for the calendar-search tool.
 */
@Serializable
data class CalendarSearchToolDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
)

/**
 * Top-level input descriptions for the chart-display tool.
 */
@Serializable
data class ChartDisplayInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val title: String? = null,
    val series: String? = null,
    val style: String? = null,
    @SerialName("x_axis") val xAxis: String? = null,
    @SerialName("y_axis") val yAxis: String? = null,
)

/**
 * Description of a single data series item in a chart-display tool call.
 */
@Serializable
data class ChartDisplaySeriesItemDescription(
    val description: String? = null,
    val name: String? = null,
    val color: String? = null,
    val points: String? = null,
    val values: String? = null,
)

/**
 * Array-level description for chart series.
 */
@Serializable
data class ChartDisplaySeriesArrayDescription(
    val description: String? = null,
    val items: ChartDisplaySeriesItemDescription? = null,
)
