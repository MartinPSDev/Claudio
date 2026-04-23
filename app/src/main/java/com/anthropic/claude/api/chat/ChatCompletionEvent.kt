package com.anthropic.claude.api.chat

import com.anthropic.claude.api.common.RateLimit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A completion event returned from the legacy (non-streaming) chat endpoint.
 * Contains the full text response, stop reason, and rate-limit metadata.
 */
@Serializable
data class ChatCompletionEvent(
    val type: String,
    val id: String,
    val completion: String,
    @SerialName("stop_reason") val stopReason: String? = null,
    val model: String,
    val stop: String? = null,
    @SerialName("log_id") val logId: String,
    val messageLimit: RateLimit,
)
