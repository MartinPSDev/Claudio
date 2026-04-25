package com.anthropic.claude.api.mcp

import kotlinx.serialization.Serializable

/**
 * Response from completing MCP OAuth auth flow.
 */
@Serializable
data class CompleteMcpAuthResponse(
    val success: Boolean = false,
    val error: String? = null
)

/**
 * Response containing a list of directory servers.
 */
@Serializable
data class DirectoryServerListResponse(
    val servers: List<DirectoryServer>? = null,
    val total: Int? = null,
    val next_cursor: String? = null
)

/**
 * A single server in the MCP directory.
 */
@Serializable
data class DirectoryServer(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val icon_url: String? = null,
    val publisher: String? = null,
    val category: String? = null,
    val requires_auth: Boolean? = null
)

/**
 * Legal attestation required for MCP server installation.
 */
@Serializable
data class McpLegalAttestation(
    val id: String? = null,
    val text: String? = null,
    val required: Boolean? = null,
    val accepted: Boolean? = null
)

/**
 * Response from creating a remote MCP server.
 */
@Serializable
data class CreateMcpRemoteServerResponse(
    val server_id: String? = null,
    val name: String? = null,
    val status: String? = null
)
