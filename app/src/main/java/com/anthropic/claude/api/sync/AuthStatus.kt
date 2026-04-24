package com.anthropic.claude.api.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Current OAuth authentication status for an MCP server connection. */
@Serializable
data class AuthStatus(
    @SerialName("is_authenticated") val isAuthenticated: Boolean = false,
)
