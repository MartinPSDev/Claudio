package com.anthropic.claude.api.chat.messages

import com.anthropic.claude.api.chat.citation.Citation
import kotlinx.serialization.Serializable

// =========================================================================
// SSE Stream Events — full hierarchy
// =========================================================================

/**
 * Content block start event, indicating a new content block in the stream.
 * Contains the block index and the initial content block data.
 */
@Serializable
data class ContentBlockStartEvent(
    val index: Int,
    val content_block: ContentBlock
) : StreamEvent

/**
 * Content block delta event, delivering incremental updates to a content block.
 */
@Serializable
data class ContentBlockDeltaEvent(
    val index: Int,
    val delta: ContentBlockDelta
) : StreamEvent

/**
 * Message start event, delivered when the assistant message begins.
 */
@Serializable
data class MessageStartEvent(
    val message: CompletionMessage
) : StreamEvent

/**
 * Message delta event, containing updates to the message metadata (e.g. stop reason).
 */
@Serializable
data class MessageDeltaEvent(
    val delta: MessageDelta? = null
) : StreamEvent

// =========================================================================
// Content Block Deltas — additional types
// =========================================================================

/**
 * Citation start delta — begins a citation inline.
 */
@Serializable
data class CitationStartDelta(
    val citation: Citation
) : ContentBlockDelta

/**
 * Citation end delta — closes a citation.
 */
@Serializable
data object CitationEndDelta : ContentBlockDelta

/**
 * Flag delta — carries content moderation flags.
 */
@Serializable
data class FlagDelta(
    val flag: MessageFlag,
    val helpline: ApiHelpline? = null
) : ContentBlockDelta

// =========================================================================
// Supporting types referenced by events
// =========================================================================

/**
 * A content block in the stream (text, tool_use, thinking, etc).
 */
@Serializable
data class ContentBlock(
    val type: String,
    val text: String? = null,
    val id: String? = null,
    val name: String? = null,
    val input: kotlinx.serialization.json.JsonElement? = null
)

/**
 * The full completion message received at stream start.
 */
@Serializable
data class CompletionMessage(
    val id: String,
    val type: String? = null,
    val role: String? = null,
    val model: String? = null,
    val content: List<ContentBlock>? = null,
    val stop_reason: String? = null,
    val stop_sequence: String? = null
)

/**
 * Delta update to the message (e.g. stop_reason update).
 */
@Serializable
data class MessageDelta(
    val stop_reason: String? = null,
    val stop_sequence: String? = null
)

/**
 * Content moderation flag attached to a message.
 */
@Serializable
data class MessageFlag(
    val type: String? = null,
    val text: String? = null
)

/**
 * API helpline information for flagged content.
 */
@Serializable
data class ApiHelpline(
    val name: String? = null,
    val phone: String? = null,
    val url: String? = null,
    val country: String? = null
)
