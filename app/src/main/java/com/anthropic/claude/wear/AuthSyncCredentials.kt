package com.anthropic.claude.wear

import kotlinx.serialization.Serializable

@Serializable
data class AuthSyncCredentials(
    val sessionKey: String,
    val orgId: String,
    val baseUrl: String,
    val voiceSelection: String? = null,
    val playbackSpeed: Float = 1.0f,
)
