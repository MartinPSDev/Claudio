package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// =========================================================================
// Chart Display — nested series/points descriptions
// =========================================================================

@Serializable
data class ChartDisplaySeriesItemPointsArrayDescription(
    val description: String? = null,
    val items: ChartDisplaySeriesItemPointsItemDescription? = null,
)

@Serializable
data class ChartDisplaySeriesItemPointsItemDescription(
    val x: String? = null,
    val y: String? = null,
    val label: String? = null,
)

// =========================================================================
// Event Create — nested array/item/recurrence/nudge descriptions
// =========================================================================

@Serializable
data class EventCreateV1InputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    @SerialName("new_events") val newEvents: String? = null,
)

@Serializable
data class EventCreateNewEventsArrayDescription(
    val description: String? = null,
    val items: EventCreateNewEventsItemDescription? = null,
)

@Serializable
data class EventCreateNewEventsItemNudgesArrayDescription(
    val description: String? = null,
    val items: EventCreateNewEventsItemNudgesItemDescription? = null,
)

@Serializable
data class EventCreateNewEventsItemNudgesItemDescription(
    @SerialName("minutes_before") val minutesBefore: String? = null,
    val method: String? = null,
)

@Serializable
data class EventCreateNewEventsItemRecurrenceDescription(
    val frequency: String? = null,
    val interval: String? = null,
    @SerialName("days_of_week") val daysOfWeek: String? = null,
    @SerialName("day_of_month") val dayOfMonth: String? = null,
    val months: String? = null,
    val position: String? = null,
    @SerialName("human_readable_frequency") val humanReadableFrequency: String? = null,
    val end: EventCreateNewEventsItemRecurrenceEndDescription? = null,
)

@Serializable
data class EventCreateNewEventsItemRecurrenceEndDescription(
    val type: String? = null,
    val date: String? = null,
    val count: String? = null,
)

@Serializable
data class EventCreateRecurrenceDescription(
    val frequency: String? = null,
    val interval: String? = null,
    @SerialName("days_of_week") val daysOfWeek: String? = null,
    @SerialName("day_of_month") val dayOfMonth: String? = null,
    val months: String? = null,
    val position: String? = null,
    @SerialName("human_readable_frequency") val humanReadableFrequency: String? = null,
    val end: EventCreateRecurrenceEndDescription? = null,
)

@Serializable
data class EventCreateRecurrenceEndDescription(
    val type: String? = null,
    val date: String? = null,
    val count: String? = null,
)

// =========================================================================
// Event Delete — nested removed-events/recurrence-span descriptions
// =========================================================================

@Serializable
data class EventDeleteRemovedEventsArrayDescription(
    val description: String? = null,
    val items: EventDeleteRemovedEventsItemDescription? = null,
)

@Serializable
data class EventDeleteRemovedEventsItemDescription(
    @SerialName("event_id") val eventId: String? = null,
    @SerialName("calendar_id") val calendarId: String? = null,
    @SerialName("recurrence_span") val recurrenceSpan: EventDeleteRemovedEventsItemRecurrenceSpanDescription? = null,
)

@Serializable
data class EventDeleteRemovedEventsItemRecurrenceSpanDescription(
    val type: String? = null,
    val description: String? = null,
)

// =========================================================================
// Event Update — nested array/nudges/recurrence descriptions
// =========================================================================

@Serializable
data class EventUpdateEventUpdatesArrayDescription(
    val description: String? = null,
    val items: EventUpdateEventUpdatesItemDescription? = null,
)

@Serializable
data class EventUpdateEventUpdatesItemNudgesArrayDescription(
    val description: String? = null,
    val items: EventUpdateEventUpdatesItemNudgesItemDescription? = null,
)

@Serializable
data class EventUpdateEventUpdatesItemNudgesItemDescription(
    @SerialName("minutes_before") val minutesBefore: String? = null,
    val method: String? = null,
)

@Serializable
data class EventUpdateEventUpdatesItemRecurrenceDescription(
    val frequency: String? = null,
    val interval: String? = null,
    @SerialName("days_of_week") val daysOfWeek: String? = null,
    @SerialName("day_of_month") val dayOfMonth: String? = null,
    val months: String? = null,
    val position: String? = null,
    @SerialName("human_readable_frequency") val humanReadableFrequency: String? = null,
    val end: EventUpdateEventUpdatesItemRecurrenceEndDescription? = null,
)

@Serializable
data class EventUpdateEventUpdatesItemRecurrenceEndDescription(
    val type: String? = null,
    val date: String? = null,
    val count: String? = null,
)

@Serializable
data class EventUpdateEventUpdatesItemRecurrenceSpanDescription(
    val type: String? = null,
    val description: String? = null,
)

// =========================================================================
// Health Connect Query — nested queries/aggregation/records/time descriptions
// =========================================================================

@Serializable
data class HealthConnectQueryQueriesArrayDescription(
    val description: String? = null,
    val items: HealthConnectQueryQueriesItemDescription? = null,
)

@Serializable
data class HealthConnectQueryQueriesItemAggregationDescription(
    val type: String? = null,
    @SerialName("bucket_size") val bucketSize: String? = null,
    @SerialName("bucket_unit") val bucketUnit: String? = null,
    val metrics: String? = null,
)

@Serializable
data class HealthConnectQueryQueriesItemRecordsDescription(
    val limit: String? = null,
    val offset: String? = null,
    @SerialName("order_by") val orderBy: String? = null,
)

@Serializable
data class HealthConnectQueryQueriesItemTimeRangeDescription(
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time") val endTime: String? = null,
)
