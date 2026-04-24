package com.anthropic.claude.api.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body to initiate an OAuth authorization flow for an MCP server.
 */
@Serializable
data class StartAuthRequest(
    @SerialName("redirect_uri") val redirectUri: String? = null,
    @SerialName("pre_auth_state") val preAuthState: String? = null,
)
