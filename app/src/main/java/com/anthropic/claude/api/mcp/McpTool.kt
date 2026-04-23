package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A tool registered on an MCP server.
 */
@Serializable
data class McpTool(
    val name: String,
    @SerialName("displayName") val displayName: String? = null,
    val description: String? = null,
    @SerialName("enabled_key") val enabledKey: String? = null,
    @SerialName("always_approved_key") val alwaysApprovedKey: String? = null,
    val annotations: JsonElement? = null,
    @SerialName("_meta") val meta: JsonElement? = null,
)
