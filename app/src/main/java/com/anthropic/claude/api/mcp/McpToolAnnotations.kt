package com.anthropic.claude.api.mcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Behavioral hints for an MCP tool, used to determine approval requirements.
 */
@Serializable
data class McpToolAnnotations(
    val title: String? = null,
    @SerialName("readOnlyHint") val readOnlyHint: Boolean? = null,
    @SerialName("destructiveHint") val destructiveHint: Boolean? = null,
    @SerialName("idempotentHint") val idempotentHint: Boolean? = null,
    @SerialName("openWorldHint") val openWorldHint: Boolean? = null,
)
