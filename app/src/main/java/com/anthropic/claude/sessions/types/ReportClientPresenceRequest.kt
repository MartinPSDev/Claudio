package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Reports the client's active presence in a remote session.
 * Sent periodically to keep the session alive.
 */
@Serializable
data class ReportClientPresenceRequest(
    @SerialName("client_id") val clientId: String? = null,
    val clear: Boolean = false,
)
