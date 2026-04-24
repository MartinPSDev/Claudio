package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for attaching an MCP prompt template to the current conversation.
 */
@Serializable
data class AttachMcpPromptRequest(
    @SerialName("server_uuid") val serverUuid: String,
    val prompt: String? = null,
    val arguments: JsonElement? = null,
)
