package com.anthropic.claude.api.conway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A webhook integration record stored on the server.
 */
@Serializable
data class WebhookRecord(
    val token: String,
    val url: String,
    @SerialName("trigger_word") val triggerWord: String,
    val label: String,
    @SerialName("created_at") val createdAt: String,
    val enabled: Boolean = true,
    @SerialName("auth_type") val authType: String? = null,
    val source: String? = null,
)

/**
 * Request body to create a new webhook integration.
 */
@Serializable
data class CreateWebhookRequest(
    @SerialName("trigger_word") val triggerWord: String,
    val label: String,
)

/**
 * Request body to update an existing webhook integration.
 */
@Serializable
data class UpdateWebhookRequest(
    val enabled: Boolean? = null,
    val label: String? = null,
)

/**
 * Request to refresh an MCP (Model Context Protocol) connection.
 */
@Serializable
data class RefreshMcpRequest(
    @SerialName("server_id") val serverId: String,
)
