package com.anthropic.claude.api.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatFeedback(
    val type: ChatFeedbackType? = null,
    val reason: String? = null,
)
