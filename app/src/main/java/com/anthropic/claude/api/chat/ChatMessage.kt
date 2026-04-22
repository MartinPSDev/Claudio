package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.MessageId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ChatMessage(
    val uuid: MessageId,
    @SerialName("parent_message_uuid") val parentMessageUuid: MessageId? = null,
    val index: Int,
    @SerialName("created_at") val createdAt: Date,
    @SerialName("updated_at") val updatedAt: Date? = null,
    @SerialName("edited_at") val editedAt: Date? = null,
    val text: String,
    val content: List<@Serializable Any>? = null,
    val sender: MessageSender,
    val attachments: List<@Serializable Any>,
    val files: List<@Serializable Any>? = null,
    @SerialName("input_mode") val inputMode: String? = null,
    @SerialName("is_edited") val isEdited: Boolean? = null,
    @SerialName("chat_feedback") val chatFeedback: ChatFeedback? = null,
    @SerialName("stop_reason") val stopReason: String? = null,
)
