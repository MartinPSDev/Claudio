package com.anthropic.claude.mcpapps.transport

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Capabilities that the host (Claude app) exposes to the MCP App.
 */
@Serializable
data class HostCapabilities(
    val openLinks: JsonObject? = null,
    val serverTools: JsonObject? = null,
    val serverResources: JsonObject? = null,
    val logging: JsonObject? = null,
    val updateModelContext: HostUpdateModelContextCapability? = null,
    val message: HostMessageCapability? = null,
)

@Serializable
data class HostUpdateModelContextCapability(
    val supported: Boolean = false,
)

@Serializable
data class HostMessageCapability(
    val supported: Boolean = false,
)
