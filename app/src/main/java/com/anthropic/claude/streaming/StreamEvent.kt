package com.anthropic.claude.streaming

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * All SSE event types emitted by the Anthropic Messages streaming API.
 *
 * The server sends events like:
 * ```
 * event: message_start
 * data: {"type":"message_start","message":{...}}
 * ```
 */
sealed interface StreamEvent {
    val type: String

    /** First event: contains the initial [Message] object with metadata. */
    @Serializable
    @SerialName("message_start")
    data class MessageStart(
        val message: JsonElement,
    ) : StreamEvent {
        override val type = "message_start"
    }

    /** Signals the start of a new content block (text, tool_use, etc.). */
    @Serializable
    @SerialName("content_block_start")
    data class ContentBlockStart(
        val index: Int,
        @SerialName("content_block") val contentBlock: JsonElement,
    ) : StreamEvent {
        override val type = "content_block_start"
    }

    /** Incremental delta for the content block at [index]. */
    @Serializable
    @SerialName("content_block_delta")
    data class ContentBlockDelta(
        val index: Int,
        val delta: JsonElement,
    ) : StreamEvent {
        override val type = "content_block_delta"
    }

    /** Signals the end of the content block at [index]. */
    @Serializable
    @SerialName("content_block_stop")
    data class ContentBlockStop(
        val index: Int,
        @SerialName("stop_timestamp") val stopTimestamp: String? = null,
    ) : StreamEvent {
        override val type = "content_block_stop"
    }

    /** Final delta with usage info and stop reason. */
    @Serializable
    @SerialName("message_delta")
    data class MessageDelta(
        val delta: JsonElement,
        val usage: JsonElement? = null,
    ) : StreamEvent {
        override val type = "message_delta"
    }

    /** Terminal event — the message is fully complete. */
    @Serializable
    @SerialName("message_stop")
    data object MessageStop : StreamEvent {
        override val type = "message_stop"
    }

    /** Server-sent heartbeat to keep the connection alive. */
    data object Ping : StreamEvent {
        override val type = "ping"
    }

    /** Error event from the server. */
    @Serializable
    @SerialName("error")
    data class Error(
        val error: JsonElement,
    ) : StreamEvent {
        override val type = "error"
    }

    /** Unknown event type (forward-compatibility). */
    data class Unknown(
        override val type: String,
        val rawData: String,
    ) : StreamEvent
}
