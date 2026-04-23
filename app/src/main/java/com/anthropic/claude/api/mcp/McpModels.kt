package com.anthropic.claude.api.mcp

import kotlinx.serialization.Serializable

/**
 * A resource exposed by an MCP server (file, URL, dataset, etc.).
 */
@Serializable
data class McpResource(
    val name: String? = null,
    val uri: String? = null,
    val description: String? = null,
    val displayName: String? = null,
    val mimeType: String? = null,
    val hidden: Boolean? = null,
)

/**
 * A named argument accepted by an [McpPrompt].
 */
@Serializable
data class McpPromptArgument(
    val name: String? = null,
    val description: String? = null,
    val required: Boolean? = null,
)

/**
 * A prompt template exposed by an MCP server.
 */
@Serializable
data class McpPrompt(
    val name: String,
    val description: String? = null,
    val displayName: String? = null,
    val arguments: List<McpPromptArgument>? = null,
)

/**
 * Response when attaching an MCP prompt resource to a conversation.
 */
@Serializable
data class AttachMcpPromptResourceResponse(
    val success: Boolean = false,
    val resourceId: String? = null,
)
