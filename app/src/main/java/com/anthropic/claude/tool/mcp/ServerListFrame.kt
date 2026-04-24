package com.anthropic.claude.tool.mcp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A frame listing all servers available from the MCP server registry.
 */
@Serializable
data class ServerListFrame(
    val servers: List<JsonElement>? = null,
)
