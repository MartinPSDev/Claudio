package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Input for the event-delete tool (v0).
 */
@Serializable
data class EventDeleteV0Input(
    @SerialName("removed_events") val removedEvents: List<JsonElement>? = null,
)

/**
 * Input for the event-update tool (v0).
 */
@Serializable
data class EventUpdateV0Input(
    @SerialName("event_updates") val eventUpdates: List<JsonElement>? = null,
)

/**
 * Input for the event-create tool (v1).
 */
@Serializable
data class EventCreateV1Input(
    @SerialName("new_events") val newEvents: List<JsonElement>? = null,
)

/**
 * Input for the ask-user tool (v0) — prompts the user with questions.
 */
@Serializable
data class AskUserInputV0Input(
    val questions: List<String>? = null,
)

/**
 * Input for the request-user-browser-takeover tool.
 */
@Serializable
data class RequestUserBrowserTakeoverInput(
    @SerialName("progress_summary") val progressSummary: String? = null,
    @SerialName("user_instructions") val userInstructions: String? = null,
)
