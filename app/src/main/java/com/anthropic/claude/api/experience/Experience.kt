package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A server-defined experience entry — associates content with a placement slot.
 */
@Serializable
data class Experience(
    val id: String,
    val key: String? = null,
    @SerialName("placement_key") val placementKey: String? = null,
    val enabled: Boolean = true,
    val content: JsonElement? = null,
)
