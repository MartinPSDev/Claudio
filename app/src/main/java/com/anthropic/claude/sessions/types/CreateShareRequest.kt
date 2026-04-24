package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to create a shareable link for a remote code session. */
@Serializable
data class CreateShareRequest(
    @SerialName("session_id") val sessionId: String? = null,
)
