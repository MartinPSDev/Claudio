package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UnknownContentBlock(
    @SerialName("start_timestamp") override val startTimestamp: Date? = null,
    @SerialName("stop_timestamp") override val stopTimestamp: Date? = null,
    override val flags: Set<MessageFlag>? = null,
) : ContentBlock
