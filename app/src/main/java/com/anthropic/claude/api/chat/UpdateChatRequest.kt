package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for updating an existing chat conversation's metadata.
 */
@Serializable
data class UpdateChatRequest(
    val name: String? = null,
    @SerialName("is_starred") val isStarred: Boolean? = null,
    val settings: JsonElement? = null,
)
