package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * An MCP (Model Context Protocol) server registered in the account.
 */
@Serializable
data class McpServer(
    val uuid: String,
    val name: String? = null,
    val url: String? = null,
    @SerialName("iconUrl") val iconUrl: String? = null,
    val connected: Boolean = false,
    @SerialName("authStatus") val authStatus: String? = null,
    @SerialName("usedAuthentication") val usedAuthentication: Boolean = false,
    @SerialName("custom_oauth_client_id") val customOauthClientId: String? = null,
    @SerialName("allowed_link_domains") val allowedLinkDomains: List<String>? = null,
    val tools: List<JsonElement>? = null,
    val resources: List<JsonElement>? = null,
    val prompts: List<JsonElement>? = null,
)
