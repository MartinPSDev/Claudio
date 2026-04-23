package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the current state of a tool invocation displayed in the chat UI.
 * Contains the tool name and the content blocks shown while it runs.
 */
@Serializable
data class ToolState(
    @SerialName("tool_name") val toolName: String,
    val content: List<ToolStateContentBlock> = emptyList(),
)

/**
 * A content block displayed inside a [ToolState] entry.
 * Discriminated by "type" field.
 */
@Serializable
sealed class ToolStateContentBlock {

    /** A plain-text content block from a tool. */
    @Serializable
    @SerialName("text")
    data class Text(val text: String) : ToolStateContentBlock()

    /** An image content block from a tool (base64 or URL). */
    @Serializable
    @SerialName("image")
    data class Image(
        val source: ImageSource? = null,
    ) : ToolStateContentBlock() {

        @Serializable
        data class ImageSource(
            val type: String? = null,
            @SerialName("media_type") val mediaType: String? = null,
            val data: String? = null,
            val url: String? = null,
        )
    }
}
