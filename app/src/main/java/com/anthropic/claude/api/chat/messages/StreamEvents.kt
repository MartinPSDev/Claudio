package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Sealed interface for SSE stream events.
 * Implemented by all event types received during chat streaming.
 */
@Serializable
@JsonClassDiscriminator("type")
sealed interface StreamEvent

/** Marker interface for content block delta types within SSE events. */
@Serializable
@JsonClassDiscriminator("type")
sealed interface ContentBlockDelta

/** SSE event: Conversation is ready for interaction. */
@Serializable
data object ConversationReadyEvent : StreamEvent

/** SSE event: Message stream has stopped. */
@Serializable
data object MessageStopEvent : StreamEvent

/** SSE delta: Incremental text content. */
@Serializable
data class TextDelta(
    val text: String
) : ContentBlockDelta

/** SSE delta: Incremental thinking/reasoning content. */
@Serializable
data class ThinkingDelta(
    val thinking: String
) : ContentBlockDelta

/** SSE delta: Incremental tool input JSON. */
@Serializable
data class InputJsonDelta(
    val partial_json: String
) : ContentBlockDelta

/** SSE delta: Incremental voice note content. */
@Serializable
data class VoiceNoteDelta(
    val audio_data: String
) : ContentBlockDelta

/** SSE delta: Thinking summary content. */
@Serializable
data class ThinkingSummaryDelta(
    val data: String
) : ContentBlockDelta
