package com.anthropic.claude.analytics.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Source screen for analytics navigation events. */
@Serializable
enum class ScreenSource {
    @SerialName("unknown")      UNKNOWN,
    @SerialName("chat")         CHAT,
    @SerialName("bell")         BELL,
    @SerialName("CHAT_SCREEN")  CHAT_SCREEN,
    @SerialName("BELL_SCREEN")  BELL_SCREEN,
}
