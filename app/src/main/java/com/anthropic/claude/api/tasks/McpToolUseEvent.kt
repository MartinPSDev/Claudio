package com.anthropic.claude.api.tasks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * An event logged when the model invokes an MCP tool during a task.
 */
@Serializable
data class McpToolUseEvent(
    @SerialName("tool_use_id") val toolUseId: String,
    @SerialName("tool_name") val toolName: String? = null,
    @SerialName("mcp_server_name") val mcpServerName: String? = null,
    val input: JsonElement? = null,
)
