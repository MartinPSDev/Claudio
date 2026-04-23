package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A chat conversation with an embedded project reference.
 * Used in conversation list responses when a conversation belongs to a project.
 */
@Serializable
data class ChatConversationWithProjectReference(
    val uuid: String,
    val name: String? = null,
    val summary: String? = null,
    val model: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("current_leaf_message_uuid") val currentLeafMessageUuid: String? = null,
    @SerialName("is_starred") val isStarred: Boolean = false,
    @SerialName("is_temporary") val isTemporary: Boolean = false,
    @SerialName("project_uuid") val projectUuid: String? = null,
    val project: JsonElement? = null,
    val settings: ChatConversationSettings? = null,
)
