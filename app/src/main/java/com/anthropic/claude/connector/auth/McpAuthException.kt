package com.anthropic.claude.connector.auth

sealed class McpAuthException(message: String) : Exception(message) {

    abstract fun errorCode(): String

    class Cancelled(message: String = "MCP auth cancelled") : McpAuthException(message) {
        override fun errorCode() = "cancelled"
    }

    class Denied(message: String = "MCP auth denied") : McpAuthException(message) {
        override fun errorCode() = "denied"
    }

    class MissingCallbackParameters(
        message: String = "Missing MCP auth callback parameters",
    ) : McpAuthException(message) {
        override fun errorCode() = "missing_callback_parameters"
    }

    class StartFailed(message: String = "MCP auth start failed") : McpAuthException(message) {
        override fun errorCode() = "start_failed"
    }

    class ExchangeFailed(
        message: String = "MCP auth token exchange failed",
        cause: Throwable? = null,
    ) : McpAuthException(message) {
        init { cause?.let { initCause(it) } }
        override fun errorCode() = "exchange_failed"
    }
}
