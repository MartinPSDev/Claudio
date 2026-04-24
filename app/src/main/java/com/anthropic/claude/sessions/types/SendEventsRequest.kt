package com.anthropic.claude.sessions.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to send a batch of events from the client to the remote session.
 */
@Serializable
data class SendEventsRequest(
    val events: List<JsonElement>? = null,
)
