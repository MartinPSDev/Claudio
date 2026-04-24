package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** SSE event: stream started, carries initial message metadata. */
@Serializable
data class MessageStartEvent(val message: JsonElement? = null)

/** SSE event: stream ended. */
@Serializable
data object MessageStopEvent

/** SSE event: conversation is ready after compaction. */
@Serializable
data object ConversationReadyEvent

/** SSE event: a content block has started (carries index + block type). */
@Serializable
data class ContentBlockStartEvent(
    val index: Int? = null,
    @SerialName("content_block") val contentBlock: JsonElement? = null,
)

/** SSE event: a delta was applied to a content block. */
@Serializable
data class ContentBlockDeltaEvent(
    val index: Int? = null,
    val delta: JsonElement? = null,
)

/** SSE event: the full message delta (stop reason, usage). */
@Serializable
data class MessageDeltaEvent(val delta: JsonElement? = null)

/** SSE delta: flag block update (crisis helpline, etc.). */
@Serializable
data class FlagDelta(
    val flag: String? = null,
    val helpline: JsonElement? = null,
)

/** SSE delta: thinking-summary text chunk. */
@Serializable
data class ThinkingSummaryDelta(val summary: String? = null)

/** SSE delta: citation start marker. */
@Serializable
data class CitationStartDelta(val citation: JsonElement? = null)

/** SSE delta: citation end marker. */
@Serializable
data class CitationEndDelta(
    @SerialName("citation_uuid") val citationUuid: String? = null,
)
