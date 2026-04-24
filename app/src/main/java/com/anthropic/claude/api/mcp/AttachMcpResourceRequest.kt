package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to attach an MCP resource (file/data) to a chat conversation.
 */
@Serializable
data class AttachMcpResourceRequest(
    @SerialName("server_uuid") val serverUuid: String? = null,
    val resource: JsonElement? = null,
)
