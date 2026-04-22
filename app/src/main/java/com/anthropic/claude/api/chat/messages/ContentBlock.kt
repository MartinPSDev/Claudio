package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
interface ContentBlock {
    val startTimestamp: Date?
    val stopTimestamp: Date?
    val flags: Set<MessageFlag>?
}
