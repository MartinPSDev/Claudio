package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to remove a flag from a specific message. */
@Serializable
data class DeleteMessageFlagRequest(
    @SerialName("flag_name") val flagName: String? = null,
)

/** How the chat conversation should be rendered in the UI. */
@Serializable
enum class RenderingMode {
    @kotlinx.serialization.SerialName("messages") MESSAGES,
}
