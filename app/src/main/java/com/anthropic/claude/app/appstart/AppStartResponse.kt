package com.anthropic.claude.app.appstart

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Response from the app-start (bootstrap) endpoint after a successful launch.
 */
@Serializable
data class AppStartResponse(
    val account: JsonElement? = null,
    val organization: JsonElement? = null,
    val features: JsonElement? = null,
)
