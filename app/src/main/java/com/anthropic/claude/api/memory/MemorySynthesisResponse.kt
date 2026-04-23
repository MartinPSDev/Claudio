package com.anthropic.claude.api.memory

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the memory synthesis endpoint.
 * Contains the synthesized memory text, optional user instructions, and last update time.
 */
@Serializable
data class MemorySynthesisResponse(
    val memory: String? = null,
    @SerialName("user_instructions") val userInstructions: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
)
