package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.Serializable

/** SSE delta carrying a chunk of the thinking (extended thinking) stream. */
@Serializable
data class ThinkingDelta(val thinking: String? = null)

/** SSE delta carrying a chunk of the text stream. */
@Serializable
data class TextDelta(val text: String? = null)
