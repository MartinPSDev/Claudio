package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// ── Event Create V1 ───────────────────────────────────────────────────────────

/** Input for the event-create tool (v1 — supports multiple events). */
@Serializable
data class EventCreateV1Input(
    @SerialName("new_events") val newEvents: JsonElement? = null,
)

@Serializable
data class EventCreateV1OutputEventCreateV1Error(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

@Serializable
data class EventCreateV1OutputEventCreateV1Result(
    @SerialName("create_results") val createResults: JsonElement? = null,
)

// ── Event Delete V0 ───────────────────────────────────────────────────────────

/** Input for the event-delete tool (v0). */
@Serializable
data class EventDeleteV0Input(
    @SerialName("removed_events") val removedEvents: JsonElement? = null,
)

@Serializable
data class EventDeleteV0OutputEventDeleteError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

@Serializable
data class EventDeleteV0OutputEventDeleteResult(
    @SerialName("delete_results") val deleteResults: JsonElement? = null,
)

// ── Event Update V0 ───────────────────────────────────────────────────────────

/** Input for the event-update tool (v0). */
@Serializable
data class EventUpdateV0Input(
    @SerialName("event_updates") val eventUpdates: JsonElement? = null,
)

@Serializable
data class EventUpdateV0OutputEventUpdateError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

@Serializable
data class EventUpdateV0OutputEventUpdateResult(
    @SerialName("update_results") val updateResults: JsonElement? = null,
)

// ── Event Search V0 ───────────────────────────────────────────────────────────

@Serializable
data class EventSearchV0OutputEventSearchError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

@Serializable
data class EventSearchV0OutputEventSearchResult(
    @SerialName("calendar_events") val calendarEvents: JsonElement? = null,
)
