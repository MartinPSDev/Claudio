package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Authentication status for an MCP server connection. */
@Serializable
enum class McpAuthStatus {
    @SerialName("not_required")   NOT_REQUIRED,
    @SerialName("authenticated")  AUTHENTICATED,
    @SerialName("AUTH_REQUIRED")  AUTH_REQUIRED,
    @SerialName("REFRESH_FAILED") REFRESH_FAILED,
}
