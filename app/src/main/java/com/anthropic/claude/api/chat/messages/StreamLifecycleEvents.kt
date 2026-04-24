package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A streaming delta event carrying a flag update for a message.
 */
@Serializable
data class FlagDelta(
    val flag: String? = null,
    val helpline: String? = null,
)

/**
 * Streaming event signaling the start of a content block.
 */
@Serializable
data class ContentBlockStartEvent(
    val index: Int = 0,
    @SerialName("content_block") val contentBlock: String? = null,
)

/**
 * Streaming event carrying a delta for an in-progress content block.
 */
@Serializable
data class ContentBlockDeltaEvent(
    val index: Int = 0,
    val delta: String? = null,
)
