package com.anthropic.claude.chat.input.draft

import com.anthropic.claude.types.strings.ChatId
import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A persisted draft of a message being composed in the chat input.
 * Survives app backgrounding and is stored locally per conversation.
 * Fields (46KB).
 */
@Serializable
data class DraftMessage(
    @SerialName("chat_id") val chatId: ChatId? = null,
    @SerialName("project_id") val projectId: ProjectId? = null,
    val text: String = "",
    val files: List<JsonElement>? = null,
    val attachments: List<JsonElement>? = null,
    val inputMode: String? = null,
    @SerialName("editMessageParentId") val editMessageParentId: String? = null,
    @SerialName("prefill_source") val prefillSource: String? = null,
    val queuedSendRequest: QueuedSendRequest? = null,
)

/**
 * A send request that is buffered locally and awaiting dispatch.
 * Queued when the network is unavailable or the app is backgrounded.
 * Fields (46KB).
 */
@Serializable
data class QueuedSendRequest(
    @SerialName("chat_id") val chatId: ChatId? = null,
    @SerialName("account_id") val accountId: String? = null,
    @SerialName("organization_id") val organizationId: String? = null,
    val text: String? = null,
    val files: List<JsonElement>? = null,
    val attachments: List<JsonElement>? = null,
    @SerialName("input_mode") val inputMode: String? = null,
    @SerialName("prefill_source") val prefillSource: String? = null,
    @SerialName("expires_at") val expiresAt: Long? = null,
)
