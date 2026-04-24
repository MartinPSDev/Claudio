package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for submitting feedback on a specific chat message.
 */
@Serializable
data class ChatFeedbackRequest(
    val type: String? = null,
    val reason: String? = null,
)
