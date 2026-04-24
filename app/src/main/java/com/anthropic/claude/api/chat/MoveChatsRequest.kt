package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for bulk-moving conversations to a project.
 */
@Serializable
data class MoveChatsRequest(
    @SerialName("conversation_uuids") val conversationUuids: List<String>,
    @SerialName("project_uuid") val projectUuid: String? = null,
)
