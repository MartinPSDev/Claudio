package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A safety flag block injected into a message when content is flagged.
 */
@Serializable
data class FlagBlock(
    @SerialName("start_timestamp") val startTimestamp: Long? = null,
    @SerialName("stop_timestamp") val stopTimestamp: Long? = null,
    val flag: String? = null,
    val helpline: String? = null,
    val flags: Int = 0,
)
