package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

// =========================================================================
// Shared Calendar Enums
// =========================================================================

/**
 * Recurrence frequency for calendar events.
 */
@Serializable
enum class RecurrenceFrequency {
    @SerialName("daily") DAILY,
    @SerialName("weekly") WEEKLY,
    @SerialName("monthly") MONTHLY,
    @SerialName("yearly") YEARLY
}

/**
 * Day of week for recurrence rules.
 */
@Serializable
enum class DaysOfWeekItem {
    @SerialName("monday") MONDAY,
    @SerialName("tuesday") TUESDAY,
    @SerialName("wednesday") WEDNESDAY,
    @SerialName("thursday") THURSDAY,
    @SerialName("friday") FRIDAY,
    @SerialName("saturday") SATURDAY,
    @SerialName("sunday") SUNDAY
}

/**
 * Recurrence end type.
 */
@Serializable
enum class RecurrenceEndType {
    @SerialName("date") DATE,
    @SerialName("count") COUNT,
    @SerialName("never") NEVER
}

/**
 * Event availability status.
 */
@Serializable
enum class EventAvailability {
    @SerialName("busy") BUSY,
    @SerialName("free") FREE,
    @SerialName("tentative") TENTATIVE
}

/**
 * Event status.
 */
@Serializable
enum class EventStatus {
    @SerialName("confirmed") CONFIRMED,
    @SerialName("tentative") TENTATIVE,
    @SerialName("cancelled") CANCELLED
}

/**
 * Nudge/reminder method.
 */
@Serializable
enum class NudgeMethod {
    @SerialName("popup") POPUP,
    @SerialName("email") EMAIL,
    @SerialName("sms") SMS
}

// =========================================================================
// Shared Calendar Sub-types
// =========================================================================

/**
 * Recurrence end specification.
 */
@Serializable
data class RecurrenceEnd(
    val type: RecurrenceEndType? = null,
    val date: String? = null,
    val count: Int? = null
)

/**
 * Recurrence rule for calendar events.
 */
@Serializable
data class EventRecurrence(
    val frequency: RecurrenceFrequency? = null,
    val interval: Int? = null,
    val days_of_week: List<DaysOfWeekItem>? = null,
    val end: RecurrenceEnd? = null
)

/**
 * Nudge/reminder for calendar events.
 */
@Serializable
data class EventNudge(
    val minutes: Int? = null,
    val method: NudgeMethod? = null
)

// =========================================================================
// EventCreate V0 Input (simplified, single event)
// =========================================================================

/**
 * Input for EventCreate V0 tool.
 */
@Serializable
data class EventCreateV0Input(
    val title: String? = null,
    val description: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val location: String? = null,
    val all_day: Boolean? = null,
    val calendar_id: String? = null,
    val recurrence: EventRecurrence? = null
)

// =========================================================================
// EventCreate V1 Input (detailed, multi-event)
// =========================================================================

/**
 * Detailed new event item for EventCreate V1.
 *
 * Fields: all_day, attendees, availability, calendar_id, end_time,
 * event_description, location, nudges, recurrence, start_time, status, title
 */
@Serializable
data class EventCreateV1InputNewEventsItem(
    val all_day: Boolean? = null,
    val attendees: List<String>? = null,
    val availability: EventAvailability? = null,
    val calendar_id: String? = null,
    val end_time: String? = null,
    val event_description: String? = null,
    val location: String? = null,
    val nudges: List<EventNudge>? = null,
    val recurrence: EventRecurrence? = null,
    val start_time: String,
    val status: EventStatus? = null,
    val title: String
)

// =========================================================================
// EventCreate V1 Output
// =========================================================================

/**
 * Output for EventCreate V1 tool.
 */
@Serializable
data class EventCreateV1Output(
    val result: EventCreateV1Result? = null,
    val error: EventCreateV1Error? = null
)

@Serializable
data class EventCreateV1Result(
    val create_results: List<EventCreateV1ResultItem>? = null
)

@Serializable
data class EventCreateV1ResultItem(
    val event_id: String? = null,
    val status: String? = null,
    val error: EventCreateV1ResultItemError? = null
)

@Serializable
data class EventCreateV1ResultItemError(
    val message: String? = null,
    val error_type: String? = null
)

@Serializable
data class EventCreateV1Error(
    val message: String? = null,
    val error_type: EventCrudErrorType? = null
)

@Serializable
enum class EventCrudErrorType {
    @SerialName("permission_denied") PERMISSION_DENIED,
    @SerialName("not_found") NOT_FOUND,
    @SerialName("invalid_input") INVALID_INPUT,
    @SerialName("calendar_not_found") CALENDAR_NOT_FOUND,
    @SerialName("conflict") CONFLICT,
    @SerialName("unknown") UNKNOWN
}

// =========================================================================
// EventDelete V0 Input (detailed)
// =========================================================================

/**
 * Detailed event delete input.
 */
@Serializable
data class EventDeleteV0InputDetailed(
    val removed_events: List<EventDeleteRemovedItem>? = null
)

@Serializable
data class EventDeleteRemovedItem(
    val event_id: String? = null,
    val calendar_id: String? = null,
    val recurrence_span: EventDeleteRecurrenceSpan? = null
)

@Serializable
data class EventDeleteRecurrenceSpan(
    val instance: EventDeleteRecurrenceSpanInstance? = null,
    val series: EventDeleteRecurrenceSpanSeries? = null
)

@Serializable
data class EventDeleteRecurrenceSpanInstance(
    val original_start_time: String? = null
)

@Serializable
data class EventDeleteRecurrenceSpanSeries(
    val delete_all: Boolean? = null
)

/**
 * Details model for event deletion UI.
 */
@Serializable
data class EventDeleteDetails(
    val event_id: String? = null,
    val title: String? = null,
    val calendar_name: String? = null
)

// =========================================================================
// EventDelete V0 Output
// =========================================================================

@Serializable
data class EventDeleteV0Output(
    val result: EventDeleteV0Result? = null,
    val error: EventDeleteV0Error? = null
)

@Serializable
data class EventDeleteV0Result(
    val delete_results: List<EventDeleteV0ResultItem>? = null
)

@Serializable
data class EventDeleteV0ResultItem(
    val event_id: String? = null,
    val deleted: Boolean? = null,
    val error: EventDeleteV0ResultItemError? = null
)

@Serializable
data class EventDeleteV0ResultItemError(
    val message: String? = null,
    val error_type: String? = null
)

@Serializable
data class EventDeleteV0Error(
    val message: String? = null,
    val error_type: EventCrudErrorType? = null
)

// =========================================================================
// EventSearch V0 Output (detailed)
// =========================================================================

@Serializable
data class EventSearchV0Output(
    val result: EventSearchV0Result? = null,
    val error: EventSearchV0Error? = null
)

@Serializable
data class EventSearchV0Result(
    val calendar_events: List<EventSearchCalendarEventsItem>? = null
)

@Serializable
data class EventSearchCalendarEventsItem(
    val calendar_id: String? = null,
    val calendar_name: String? = null,
    val events: List<EventSearchEventItem>? = null
)

@Serializable
data class EventSearchEventItem(
    val event_id: String? = null,
    val title: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val location: String? = null,
    val description: String? = null,
    val all_day: Boolean? = null,
    val availability: EventAvailability? = null,
    val status: EventStatus? = null,
    val nudges: List<EventNudge>? = null,
    val recurrence: EventRecurrence? = null
)

@Serializable
data class EventSearchV0Error(
    val message: String? = null,
    val error_type: EventCrudErrorType? = null
)

// =========================================================================
// EventUpdate V0 Input (detailed)
// =========================================================================

/**
 * Detailed event update input.
 */
@Serializable
data class EventUpdateV0InputDetailed(
    val event_updates: List<EventUpdateItem>? = null
)

@Serializable
data class EventUpdateItem(
    val event_id: String? = null,
    val calendar_id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val location: String? = null,
    val all_day: Boolean? = null,
    val availability: EventAvailability? = null,
    val status: EventStatus? = null,
    val nudges: List<EventNudge>? = null,
    val recurrence: EventRecurrence? = null,
    val recurrence_span: EventUpdateRecurrenceSpan? = null
)

@Serializable
data class EventUpdateRecurrenceSpan(
    val instance: EventUpdateRecurrenceSpanInstance? = null,
    val series: EventUpdateRecurrenceSpanSeries? = null
)

@Serializable
data class EventUpdateRecurrenceSpanInstance(
    val original_start_time: String? = null
)

@Serializable
data class EventUpdateRecurrenceSpanSeries(
    val update_all: Boolean? = null
)

/**
 * Details model for event update UI.
 */
@Serializable
data class EventUpdateDetails(
    val event_id: String? = null,
    val title: String? = null,
    val calendar_name: String? = null,
    val changes: Map<String, String>? = null
)

// =========================================================================
// EventUpdate V0 Output
// =========================================================================

@Serializable
data class EventUpdateV0Output(
    val result: EventUpdateV0Result? = null,
    val error: EventUpdateV0Error? = null
)

@Serializable
data class EventUpdateV0Result(
    val update_results: List<EventUpdateV0ResultItem>? = null
)

@Serializable
data class EventUpdateV0ResultItem(
    val event_id: String? = null,
    val updated: Boolean? = null,
    val updated_event: EventUpdateResultUpdatedEvent? = null,
    val error: EventUpdateV0ResultItemError? = null
)

@Serializable
data class EventUpdateResultUpdatedEvent(
    val event_id: String? = null,
    val title: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val location: String? = null,
    val description: String? = null,
    val all_day: Boolean? = null,
    val availability: EventAvailability? = null,
    val status: EventStatus? = null,
    val nudges: List<EventNudge>? = null,
    val recurrence: EventRecurrence? = null
)

@Serializable
data class EventUpdateV0ResultItemError(
    val message: String? = null,
    val error_type: String? = null
)

@Serializable
data class EventUpdateV0Error(
    val message: String? = null,
    val error_type: EventCrudErrorType? = null
)

// =========================================================================
// Calendar Update Preview
// =========================================================================

/**
 * Preview data for calendar updates.
 */
@Serializable
data class CalendarUpdatePreviewData(
    val event_id: String? = null,
    val title: String? = null,
    val original_start: String? = null,
    val new_start: String? = null,
    val calendar_name: String? = null
)

// =========================================================================
// Calendar Search Result Details
// =========================================================================

/**
 * Detailed calendar search result with access level.
 */
@Serializable
data class CalendarSearchV0OutputCalendarSearchResult(
    val calendars: List<CalendarSearchResultCalendarsItem>? = null
)

@Serializable
data class CalendarSearchResultCalendarsItem(
    val calendar_id: String? = null,
    val calendar_name: String? = null,
    val account_name: String? = null,
    val access_level: CalendarAccessLevel? = null
)

@Serializable
enum class CalendarAccessLevel {
    @SerialName("owner") OWNER,
    @SerialName("editor") EDITOR,
    @SerialName("read") READ,
    @SerialName("freebusy") FREEBUSY,
    @SerialName("none") NONE
}
