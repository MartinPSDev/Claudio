package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Output of the request-user-browser-takeover tool.
 * Provides the URL the agent navigates to during a computer-use session.
 */
@Serializable
data class RequestUserBrowserTakeoverOutput(
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("takeover_url") val takeoverUrl: String? = null,
)
