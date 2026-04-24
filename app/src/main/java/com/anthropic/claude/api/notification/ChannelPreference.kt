package com.anthropic.claude.api.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Per-channel notification preference settings for a user.
 */
@Serializable
data class ChannelPreference(
    @SerialName("enable_email") val enableEmail: Boolean = false,
    @SerialName("enable_push") val enablePush: Boolean = false,
)
