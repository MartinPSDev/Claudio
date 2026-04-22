package com.anthropic.claude.mcpapps.transport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Parameters sent by the MCP App client during the initialize handshake.
 */
@Serializable
data class McpInitializeParams(
    @SerialName("protocolVersion") val protocolVersion: String,
    val capabilities: McpClientCapabilities = McpClientCapabilities(),
    val clientInfo: HostInfo? = null,
)

@Serializable
data class McpClientCapabilities(
    val sampling: JsonObject? = null,
    val roots: JsonObject? = null,
)

/**
 * Capabilities advertised by the MCP server (the App) to the host (Claude).
 */
@Serializable
data class McpServerCapabilities(
    val tools: JsonObject? = null,
    val resources: JsonObject? = null,
    val prompts: JsonObject? = null,
)
