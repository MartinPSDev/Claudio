package com.anthropic.claude.tool.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * MCP server tools list frame — holds the tools exposed by a server.
 */
@Serializable
data class ToolsFrame(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val tools: List<JsonElement>? = null,
)

/**
 * MCP server resources list frame — holds the resources exposed by a server.
 */
@Serializable
data class ResourcesFrame(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val resources: List<JsonElement>? = null,
)

/**
 * MCP server prompts list frame — holds the prompt templates exposed by a server.
 */
@Serializable
data class PromptsFrame(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val prompts: List<JsonElement>? = null,
)

/**
 * A lightweight stub representing a server in a server list response.
 */
@Serializable
data class ServerStub(
    val uuid: String,
    val name: String? = null,
    val url: String? = null,
)
