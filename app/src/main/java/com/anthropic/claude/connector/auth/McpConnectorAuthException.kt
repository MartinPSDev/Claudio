package com.anthropic.claude.connector.auth

/**
 * Exception thrown when MCP connector OAuth authentication fails or has no pending connector.
 */
class McpConnectorAuthException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {
    companion object {
        const val APP_SOURCE = "claude-mobile"
    }
}
