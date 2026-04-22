package com.anthropic.claude.sessions.types

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Payload returned by Claude in response to a control request.
 * Carries the user's decision on a tool-use permission, updated inputs, or a message.
 */
@Serializable
data class ControlResponsePayload(
    val behavior: String? = null,
    val toolUseID: String? = null,
    val updatedInput: Map<String, JsonElement>? = null,
    val updatedPermissions: List<PermissionUpdate>? = null,
    val message: String? = null,
    val action: String? = null,
    val content: Map<String, JsonElement>? = null,
)
