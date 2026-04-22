package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MessageSender {
    @SerialName("human") HUMAN,
    @SerialName("assistant") ASSISTANT,
}
