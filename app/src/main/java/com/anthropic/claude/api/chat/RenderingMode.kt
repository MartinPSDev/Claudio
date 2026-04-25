package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rendering mode for chat messages.
 *
 * Values: RAW ("raw"), MESSAGES ("messages")
 */
@Serializable
enum class RenderingMode(val value: String) {
    @SerialName("raw")
    RAW("raw"),

    @SerialName("messages")
    MESSAGES("messages");

    override fun toString(): String = value
}
