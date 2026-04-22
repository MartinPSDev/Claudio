package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.Serializable

@Serializable
data class ThinkingSummary(
    val summary: String,
)
