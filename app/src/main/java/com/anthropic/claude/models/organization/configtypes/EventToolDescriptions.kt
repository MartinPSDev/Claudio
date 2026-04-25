package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Shared recurrence description used across event-create and event-update tools. */
@Serializable
data class EventRecurrenceDescription(
    val frequency: String? = null,
    val interval: Int? = null,
    @SerialName("days_of_week")             val daysOfWeek: String? = null,
    @SerialName("day_of_month")             val dayOfMonth: Int? = null,
    val months: String? = null,
    val position: Int? = null,
    @SerialName("human_readable_frequency") val humanReadableFrequency: String? = null,
    val end: String? = null,
)

/** Per-event-item description for the event-create tool. */
@Serializable
data class EventCreateNewEventsItemDescription(
    @SerialName("tool_description") val toolDescription: String? = null,
    @SerialName("calendar_id")      val calendarId: String? = null,
    val title: String? = null,
    @SerialName("all_day")          val allDay: String? = null,
    @SerialName("start_time")       val startTime: String? = null,
    @SerialName("end_time")         val endTime: String? = null,
    val location: String? = null,
    @SerialName("event_description") val eventDescription: String? = null,
    val attendees: String? = null,
    val availability: String? = null,
    val nudges: String? = null,
    val recurrence: String? = null,
)

/** Per-event-item description for the event-update tool. */
@Serializable
data class EventUpdateEventUpdatesItemDescription(
    @SerialName("tool_description") val toolDescription: String? = null,
    @SerialName("event_id")         val eventId: String? = null,
    @SerialName("calendar_id")      val calendarId: String? = null,
    val title: String? = null,
    @SerialName("all_day")          val allDay: String? = null,
    @SerialName("start_time")       val startTime: String? = null,
    @SerialName("end_time")         val endTime: String? = null,
    val location: String? = null,
    @SerialName("event_description") val eventDescription: String? = null,
    val attendees: String? = null,
    val availability: String? = null,
)

/** Tool input descriptions for the event-create tool. */
@Serializable
data class EventCreateInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val title: String? = null,
    val description: String? = null,
    @SerialName("all_day")    val allDay: String? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time")   val endTime: String? = null,
    val location: String? = null,
    val recurrence: String? = null,
)

/** Tool input descriptions for the event-search tool. */
@Serializable
data class EventSearchInputDescriptions(
    @SerialName("tool_description")  val toolDescription: String? = null,
    @SerialName("calendar_id")       val calendarId: String? = null,
    @SerialName("start_time")        val startTime: String? = null,
    @SerialName("end_time")          val endTime: String? = null,
    @SerialName("include_all_day")   val includeAllDay: String? = null,
    val limit: String? = null,
)

/** Tool input descriptions for the event-delete tool. */
@Serializable
data class EventDeleteInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    @SerialName("event_id")         val eventId: String? = null,
    @SerialName("calendar_id")      val calendarId: String? = null,
    @SerialName("recurrence_span")  val recurrenceSpan: String? = null,
)

/** Tool input descriptions for the event-update tool. */
@Serializable
data class EventUpdateInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    @SerialName("event_id")         val eventId: String? = null,
    @SerialName("calendar_id")      val calendarId: String? = null,
    val title: String? = null,
    @SerialName("start_time")       val startTime: String? = null,
    @SerialName("end_time")         val endTime: String? = null,
    val location: String? = null,
    val recurrence: String? = null,
)
