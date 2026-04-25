package com.anthropic.claude.api.errors

/**
 * Exception thrown when the Claude API returns an error response.
 *
 * The error field (A in Smali, type kr2) contains the parsed API error body.
 * The message is constructed as: "$contextMessage: $error" or just "$error".
 */
class ClaudeApiErrorException(
    val error: ApiError,
    contextMessage: String? = null
) : RuntimeException(
    if (contextMessage != null) "$contextMessage: $error" else error.toString()
) {
    /**
     * Represents the parsed error body from the Claude API.
     * The obfuscated type kr2 maps to this structure.
     */
    data class ApiError(
        val type: String? = null,
        val message: String? = null,
        val statusCode: Int? = null
    )
}
