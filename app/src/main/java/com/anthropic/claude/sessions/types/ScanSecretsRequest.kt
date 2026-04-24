package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request to scan text content for secrets (API keys, credentials, etc.)
 * in a remote code session.
 */
@Serializable
data class ScanSecretsRequest(
    @SerialName("text_content") val textContent: String? = null,
)
