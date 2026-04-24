package com.anthropic.claude.api.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for finishing an OAuth authorization flow and exchanging
 * the auth code for a session token.
 */
@Serializable
data class FinishAuthRequest(
    val code: String,
    val state: String? = null,
    val scope: String? = null,
    @SerialName("redirect_uri") val redirectUri: String? = null,
)
