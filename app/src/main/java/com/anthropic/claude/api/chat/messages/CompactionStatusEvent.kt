package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A streaming event signaling the context compaction status of a conversation.
 */
@Serializable
data class CompactionStatusEvent(
    val status: String? = null,
    val message: String? = null,
)
