package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** SSE event wrapping a message delta (text/thinking increments). */
@Serializable
data class MessageDeltaEvent(val delta: JsonElement? = null)

/** SSE event signaling the start of a new message. */
@Serializable
data class MessageStartEvent(val message: JsonElement? = null)

/** SSE event delivering a citation start within a streaming message. */
@Serializable
data class CitationStartDelta(val citation: JsonElement? = null)
