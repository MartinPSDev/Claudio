package com.anthropic.claude.tool.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// =========================================================================
// Map & Places Tools
// =========================================================================

/**
 * Input for the MapDisplay tool.
 */
@Serializable
data class MapDisplayV0Input(
    val markers: List<MapMarker>? = null,
    val title: String? = null
)

@Serializable
data class MapMarker(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val label: String? = null,
    val description: String? = null
)

/**
 * Output for the PlacesMapDisplay tool.
 */
@Serializable
data class PlacesMapDisplayV0Output(
    val enriched_places: List<EnrichedPlace>? = null
)

@Serializable
data class EnrichedPlace(
    val name: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val rating: Double? = null,
    val place_id: String? = null,
    val photo_url: String? = null
)

// =========================================================================
// Chart Tool
// =========================================================================

/**
 * Output for the ChartDisplay tool.
 */
@Serializable
data class ChartDisplayV0Output(
    val message: String? = null,
    val status: String? = null
)

// =========================================================================
// Task Tool
// =========================================================================

/**
 * Input for the TaskPropose tool.
 */
@Serializable
data class TaskProposeInput(
    val context: String? = null,
    val steps: List<TaskStep>? = null,
    val title: String? = null
)

@Serializable
data class TaskStep(
    val description: String? = null,
    val tool_name: String? = null,
    val tool_input: JsonElement? = null
)

// =========================================================================
// Message Compose Tools
// =========================================================================

/**
 * Input for MessageCompose V0 tool.
 */
@Serializable
data class MessageComposeV0Input(
    val kind: String? = null,
    val summary_title: String? = null,
    val variants: List<ComposeVariant>? = null
)

/**
 * Input for MessageCompose V1 tool.
 */
@Serializable
data class MessageComposeV1Input(
    val kind: String? = null,
    val summary_title: String? = null,
    val variants: List<ComposeVariant>? = null
)

@Serializable
data class ComposeVariant(
    val subject: String? = null,
    val body: String? = null,
    val tone: String? = null
)

// =========================================================================
// Calendar Event Tools (detailed)
// =========================================================================

/**
 * Input for EventCreate V1 tool.
 */
@Serializable
data class EventCreateV1Input(
    val new_events: List<CalendarEventInput>? = null
)

@Serializable
data class CalendarEventInput(
    val title: String? = null,
    val description: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val location: String? = null,
    val all_day: Boolean? = null,
    val recurrence: String? = null,
    val calendar_id: String? = null,
    val attendees: List<String>? = null,
    val reminders: List<Int>? = null
)

/**
 * Input for EventDelete V0 tool.
 */
@Serializable
data class EventDeleteV0Input(
    val event_id: String? = null,
    val calendar_id: String? = null
)

/**
 * Input for EventUpdate V0 tool.
 */
@Serializable
data class EventUpdateV0Input(
    val event_id: String? = null,
    val calendar_id: String? = null,
    val updates: CalendarEventInput? = null
)

/**
 * Input for EventSearch V0 tool.
 */
@Serializable
data class EventSearchV0Input(
    val query: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val calendar_id: String? = null,
    val max_results: Int? = null
)

// =========================================================================
// Calendar Event Outputs (sealed result/error)
// =========================================================================

@Serializable
sealed interface EventCreateV1OutputResult {
    @Serializable
    data class Success(val event_ids: List<String>? = null) : EventCreateV1OutputResult

    @Serializable
    data class Error(val error_code: String? = null, val message: String? = null) : EventCreateV1OutputResult
}

@Serializable
sealed interface EventDeleteV0OutputResult {
    @Serializable
    data class Success(val deleted: Boolean = true) : EventDeleteV0OutputResult

    @Serializable
    data class Error(val error_code: String? = null, val message: String? = null) : EventDeleteV0OutputResult
}

@Serializable
sealed interface EventUpdateV0OutputResult {
    @Serializable
    data class Success(val updated: Boolean = true) : EventUpdateV0OutputResult

    @Serializable
    data class Error(val error_code: String? = null, val message: String? = null) : EventUpdateV0OutputResult
}

@Serializable
sealed interface EventSearchV0OutputResult {
    @Serializable
    data class Success(val events: List<CalendarSearchResultEvent>? = null) : EventSearchV0OutputResult

    @Serializable
    data class Error(val error_code: String? = null, val message: String? = null) : EventSearchV0OutputResult
}

@Serializable
data class CalendarSearchResultEvent(
    val event_id: String? = null,
    val title: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val location: String? = null,
    val description: String? = null,
    val calendar_id: String? = null,
    val all_day: Boolean? = null
)

// =========================================================================
// Health Connect Tools (detailed)
// =========================================================================

/**
 * Input for HealthConnectQuery V0 tool.
 */
@Serializable
data class HealthConnectQueryV0Input(
    val queries: List<HealthQuery>? = null
)

@Serializable
data class HealthQuery(
    val data_type: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val limit: Int? = null
)

@Serializable
sealed interface HealthConnectQueryV0OutputResult {
    @Serializable
    data class Success(val records: List<JsonElement>? = null) : HealthConnectQueryV0OutputResult

    @Serializable
    data class Error(val error_code: String? = null, val message: String? = null) : HealthConnectQueryV0OutputResult
}

/**
 * Detailed health tools configuration.
 */
@Serializable
data class HealthToolsConfigDetailed(
    val health_connect_data_types_v0: HealthConnectDataTypesToolConfig? = null,
    val health_connect_query_v0: HealthConnectQueryToolConfig? = null
)

@Serializable
data class HealthConnectDataTypesToolConfig(
    val enabled: Boolean = false
)

@Serializable
data class HealthConnectQueryToolConfig(
    val enabled: Boolean = false,
    val max_records: Int? = null
)

// =========================================================================
// User Location Output (detailed)
// =========================================================================

@Serializable
sealed interface UserLocationV0OutputResult {
    @Serializable
    data class Success(
        val latitude: Double? = null,
        val longitude: Double? = null,
        val accuracy: Float? = null,
        val address: String? = null,
        val city: String? = null,
        val country: String? = null,
        val timezone: String? = null
    ) : UserLocationV0OutputResult

    @Serializable
    data class Error(
        val error_code: String? = null,
        val message: String? = null
    ) : UserLocationV0OutputResult
}

// =========================================================================
// Request Form Input
// =========================================================================

/**
 * Input for the RequestFormInputFromUser tool.
 */
@Serializable
data class RequestFormInputFromUserInput(
    val title: String? = null,
    val description: String? = null,
    val fields: List<FormField>? = null
)

@Serializable
data class FormField(
    val name: String? = null,
    val label: String? = null,
    val type: String? = null,
    val required: Boolean? = null,
    val placeholder: String? = null,
    val options: List<String>? = null,
    val default_value: String? = null
)

// =========================================================================
// Phone Call Output
// =========================================================================

/**
 * Output for the PhoneCallCompleted tool.
 */
@Serializable
data class PhoneCallCompletedOutput(
    val status: String? = null,
    val duration_seconds: Int? = null,
    val transcript: String? = null,
    val call_id: String? = null
)
