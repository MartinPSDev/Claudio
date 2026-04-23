package com.anthropic.claude.connector.auth

/**
 * Base sealed exception hierarchy for MCP OAuth authentication errors.
 * Each subtype represents a distinct failure mode in the auth flow.
 */
sealed class McpAuthException(message: String, cause: Throwable? = null) :
    Exception(message, cause) {

    /** Returns a machine-readable error code identifying this exception. */
    abstract val errorCode: String

    /** The OAuth token exchange with the MCP server failed. */
    class ExchangeFailed(
        message: String,
        cause: Throwable? = null,
    ) : McpAuthException(message, cause) {
        override val errorCode = "exchange_failed"
    }

    /** The user explicitly denied the authorization request. */
    class Denied(
        message: String = "User denied authorization",
        cause: Throwable? = null,
    ) : McpAuthException(message, cause) {
        override val errorCode = "denied"
    }

    /** The OAuth callback was missing required parameters (e.g. 'code'). */
    class MissingCallbackParameters :
        McpAuthException("Authentication failed: missing 'code' parameter") {
        override val errorCode = "missing_code"
    }

    /** Launching the auth browser/activity failed. */
    class StartFailed(
        message: String,
        cause: Throwable? = null,
    ) : McpAuthException(message, cause) {
        override val errorCode = "start_failed"
    }

    /** The user cancelled the auth flow (e.g. closed the browser tab). */
    class Cancelled(
        message: String = "Authentication cancelled",
        cause: Throwable? = null,
    ) : McpAuthException(message, cause) {
        override val errorCode = "cancelled"
    }
}
