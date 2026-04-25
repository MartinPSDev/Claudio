package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateChatTitleRequest(
    @SerialName("message_content")
    val messageContent: String,
    @SerialName("recent_titles")
    val recentTitles: List<String> = emptyList()
)
