package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Output of the chart-display tool (v0).
 */
@Serializable
data class ChartDisplayV0Output(
    val message: String? = null,
    val status: String? = null,
)

/**
 * Output of the places-map-display tool (v0).
 */
@Serializable
data class PlacesMapDisplayV0Output(
    @SerialName("enriched_places") val enrichedPlaces: List<JsonElement>? = null,
)

/**
 * Input for the phone-use tool.
 */
@Serializable
data class PhoneUseInput(
    val task: String? = null,
    @SerialName("task_description") val taskDescription: String? = null,
    @SerialName("to_number") val toNumber: String? = null,
)

/**
 * Result of a calendar search (v0).
 */
@Serializable
data class CalendarSearchV0OutputCalendarSearchResult(
    val calendars: List<JsonElement>? = null,
)

/**
 * Result of an event-create tool call (v1).
 */
@Serializable
data class EventCreateV1OutputEventCreateV1Result(
    @SerialName("create_results") val createResults: List<JsonElement>? = null,
)

/**
 * Result of an event-search tool call (v0).
 */
@Serializable
data class EventSearchV0OutputEventSearchResult(
    @SerialName("calendar_events") val calendarEvents: List<JsonElement>? = null,
)

/**
 * Result of an event-delete tool call (v0).
 */
@Serializable
data class EventDeleteV0OutputEventDeleteResult(
    @SerialName("delete_results") val deleteResults: List<JsonElement>? = null,
)
