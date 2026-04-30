package com.anthropic.claude.streaming

import android.util.Log
import kotlinx.serialization.json.Json

/**
 * Parses raw SSE (Server-Sent Events) lines into structured [SseFrame]s.
 *
 * SSE format (per W3C spec):
 * ```
 * event: <event-type>
 * data: <json-payload>
 * id: <optional-id>
 * retry: <milliseconds>
 * ```
 *
 * Lines are separated by `\n`, `\r\n`, or `\r`. A blank line dispatches the event.
 */
class SseLineParser {

    private var currentEvent: String? = null
    private var currentData = StringBuilder()
    private var currentId: String? = null
    private var retryMs: Long? = null

    /**
     * Feeds a single line from the stream and returns a completed [SseFrame]
     * if this line triggers event dispatch (blank line), or null otherwise.
     */
    fun feedLine(line: String): SseFrame? {
        // Blank line = dispatch event
        if (line.isEmpty() || line == "\r" || line == "\r\n") {
            return dispatchEvent()
        }

        when {
            line.startsWith("data: ") -> currentData.appendLine(line.removePrefix("data: "))
            line.startsWith("data:") -> currentData.appendLine(line.removePrefix("data:"))
            line == "data\r\n" || line == "data\r" || line == "data\n" || line == "data" ->
                currentData.appendLine("")

            line.startsWith("event: ") -> currentEvent = line.removePrefix("event: ").trim()
            line.startsWith("event:") -> currentEvent = line.removePrefix("event:").trim()
            line == "event\r\n" || line == "event\r" || line == "event\n" ->
                currentEvent = ""

            line.startsWith("id: ") -> currentId = line.removePrefix("id: ").trim()
            line.startsWith("id:") -> currentId = line.removePrefix("id:").trim()
            line == "id\r\n" || line == "id\r" || line == "id\n" ->
                currentId = ""

            line.startsWith("retry: ") -> {
                retryMs = line.removePrefix("retry: ").trim().toLongOrNull()
            }
            line.startsWith("retry:") -> {
                retryMs = line.removePrefix("retry:").trim().toLongOrNull()
            }

            line.startsWith(":") -> { /* SSE comment, ignore */ }
        }

        return null
    }

    private fun dispatchEvent(): SseFrame? {
        val data = currentData.toString().trimEnd('\n', '\r')
        val event = currentEvent
        val id = currentId
        val retry = retryMs

        // Reset state
        currentEvent = null
        currentData = StringBuilder()
        currentId = null
        retryMs = null

        if (data.isEmpty() && event == null) return null

        return SseFrame(
            event = event,
            data = data,
            id = id,
            retryMs = retry,
        )
    }

    /** Resets the parser state (e.g. on reconnection). */
    fun reset() {
        currentEvent = null
        currentData = StringBuilder()
        currentId = null
        retryMs = null
    }

    companion object {
        private const val TAG = "SseLineParser"
    }
}

/**
 * A single parsed SSE frame before deserialization into a [StreamEvent].
 */
data class SseFrame(
    val event: String?,
    val data: String,
    val id: String? = null,
    val retryMs: Long? = null,
)

/**
 * Converts a raw [SseFrame] into a typed [StreamEvent].
 */
object StreamEventDeserializer {

    private val json = Json { ignoreUnknownKeys = true }

    fun deserialize(frame: SseFrame): StreamEvent {
        val eventType = frame.event ?: return StreamEvent.Unknown("unknown", frame.data)

        return try {
            when (eventType) {
                "message_start" -> json.decodeFromString<StreamEvent.MessageStart>(frame.data)
                "content_block_start" -> json.decodeFromString<StreamEvent.ContentBlockStart>(frame.data)
                "content_block_delta" -> json.decodeFromString<StreamEvent.ContentBlockDelta>(frame.data)
                "content_block_stop" -> json.decodeFromString<StreamEvent.ContentBlockStop>(frame.data)
                "message_delta" -> json.decodeFromString<StreamEvent.MessageDelta>(frame.data)
                "message_stop" -> StreamEvent.MessageStop
                "ping" -> StreamEvent.Ping
                "error" -> json.decodeFromString<StreamEvent.Error>(frame.data)
                else -> StreamEvent.Unknown(eventType, frame.data)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to parse SSE event '$eventType': ${e.message}")
            StreamEvent.Unknown(eventType, frame.data)
        }
    }

    private const val TAG = "StreamEventDeserializer"
}
