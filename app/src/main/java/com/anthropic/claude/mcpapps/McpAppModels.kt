package com.anthropic.claude.mcpapps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An image embedded in an MCP app response — type, MIME type, and base64 data.
 */
@Serializable
data class HydratedImageContent(
    val type: String? = null,
    @SerialName("mimeType") val mimeType: String? = null,
    val data: String? = null,
)

/**
 * Thrown when an MCP app HTTP fetch fails.
 */
class McpAppFetchException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Thrown when the model context window is too large for an MCP operation.
 */
class ModelContextTooLargeException(
    message: String = "Model context exceeds maximum allowed size",
) : Exception(message)

/**
 * Thrown when a domain validation check fails for an MCP URL or identifier.
 */
class DomainValidationException(
    message: String,
) : Exception(message)
