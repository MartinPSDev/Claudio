package com.anthropic.claude.mcpapps.transport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response to the MCP Apps protocol initialize message.
 * Sent by the host (Claude) to confirm the connection.
 */
@Serializable
data class InitializeResult(
    @SerialName("protocolVersion") val protocolVersion: String,
    @SerialName("hostInfo") val hostInfo: HostInfo,
    @SerialName("hostCapabilities") val hostCapabilities: HostCapabilities,
    @SerialName("hostContext") val hostContext: HostContext,
)

@Serializable
data class HostInfo(
    val name: String = "Claude",
    val version: String? = null,
)
