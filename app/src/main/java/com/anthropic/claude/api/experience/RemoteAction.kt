package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A server-defined remote action that can be triggered from an experience.
 */
@Serializable
data class RemoteAction(
    val id: String? = null,
    val params: JsonElement? = null,
)
