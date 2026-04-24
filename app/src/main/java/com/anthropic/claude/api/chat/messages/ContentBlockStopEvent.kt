package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Streaming event signaling the end of a content block.
 */
@Serializable
data class ContentBlockStopEvent(
    val index: Int = 0,
    @SerialName("stop_timestamp") val stopTimestamp: String? = null,
)
