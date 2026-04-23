package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.ChatId
import com.anthropic.claude.types.strings.MessageId
import com.anthropic.claude.types.strings.ModelId
import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A full conversation record including its most recent nested message.
 * Used in conversation list responses and search results.
 */
@Serializable
data class ChatConversationWithNestedMessage(
    val uuid: ChatId? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    val name: String? = null,
    val summary: String? = null,
    val model: ModelId? = null,
    val settings: ChatConversationSettings? = null,
    @SerialName("is_starred") val isStarred: Boolean = false,
    @SerialName("project_uuid") val projectUuid: ProjectId? = null,
    @SerialName("chat_messages") val chatMessages: List<ChatMessage>? = null,
    @SerialName("is_temporary") val isTemporary: Boolean = false,
    @SerialName("current_leaf_message_uuid") val currentLeafMessageUuid: MessageId? = null,
)

/** Per-conversation settings controlled by the user. */
@Serializable
data class ChatConversationSettings(
    @SerialName("model_fallback") val modelFallback: Boolean? = null,
    @SerialName("paper_mode") val paperMode: Boolean? = null,
)
