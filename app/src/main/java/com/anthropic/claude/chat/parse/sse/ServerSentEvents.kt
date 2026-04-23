package com.anthropic.claude.chat.parse.sse

/**
 * Holds metadata about a Server-Sent Event parse error.
 */
data class ServerSentEventError(
    val message: String,
    val rawLine: String? = null,
)

/**
 * Thrown when a Server-Sent Event cannot be parsed from the stream.
 */
class ServerSentEventException(
    val error: ServerSentEventError,
) : Exception("SSE parse error: ${error.message} (raw='${error.rawLine}')")
