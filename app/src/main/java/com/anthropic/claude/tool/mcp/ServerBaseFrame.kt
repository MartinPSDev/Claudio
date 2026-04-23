package com.anthropic.claude.tool.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Base frame for an MCP server connection, containing core identity
 * and authentication state fields shared across server representations.
 */
@Serializable
data class ServerBaseFrame(
    val uuid: String,
    val name: String? = null,
    val url: String? = null,
    @SerialName("iconUrl") val iconUrl: String? = null,
    val connected: Boolean = false,
    @SerialName("authStatus") val authStatus: String? = null,
    @SerialName("usedAuthentication") val usedAuthentication: Boolean = false,
    @SerialName("custom_oauth_client_id") val customOauthClientId: String? = null,
)
