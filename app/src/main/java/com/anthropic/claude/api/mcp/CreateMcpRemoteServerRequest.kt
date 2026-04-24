package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for registering a new remote MCP server.
 */
@Serializable
data class CreateMcpRemoteServerRequest(
    val name: String,
    val url: String? = null,
    val attestations: JsonElement? = null,
)
