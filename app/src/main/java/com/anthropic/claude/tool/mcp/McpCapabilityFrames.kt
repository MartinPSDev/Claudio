package com.anthropic.claude.tool.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** MCP frame carrying a list of tools for a given server. */
@Serializable
data class ToolsFrame(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val tools: JsonElement? = null,
)

/** MCP frame carrying a list of resources for a given server. */
@Serializable
data class ResourcesFrame(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val resources: JsonElement? = null,
)

/** MCP frame carrying a list of prompts for a given server. */
@Serializable
data class PromptsFrame(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val prompts: JsonElement? = null,
)
