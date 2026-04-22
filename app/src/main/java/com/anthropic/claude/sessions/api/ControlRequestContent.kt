package com.anthropic.claude.sessions.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Content payload of a control request sent from the bridge to the Claude session.
 * Used for tool-use permission prompts, MCP actions, and custom messages.
 */
@Serializable
data class ControlRequestContent(
    val subtype: String? = null,
    @SerialName("tool_use_id") val toolUseId: String? = null,
    @SerialName("tool_name") val toolName: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    val title: String? = null,
    val description: String? = null,
    val input: Map<String, JsonElement>? = null,
    @SerialName("permission_suggestions") val permissionSuggestions: List<PermissionUpdate>? = null,
    @SerialName("mcp_server_name") val mcpServerName: String? = null,
    val message: String? = null,
    @SerialName("requested_schema") val requestedSchema: JsonElement? = null,
)

/** A single permission update suggestion returned inside a control request. */
@Serializable
data class PermissionUpdate(
    val action: String? = null,
    val resource: String? = null,
    val allow: Boolean = false,
)
