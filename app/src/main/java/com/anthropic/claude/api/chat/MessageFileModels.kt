package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * An image file attached to a chat message.
 */
@Serializable
data class MessageImageFile(
    @SerialName("file_uuid") val fileUuid: String,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("sanitized_name") val sanitizedName: String? = null,
    val path: String? = null,
    @SerialName("size_bytes") val sizeBytes: Long? = null,
    @SerialName("preview_asset") val previewAsset: JsonElement? = null,
    @SerialName("thumbnail_asset") val thumbnailAsset: JsonElement? = null,
    @SerialName("created_at") val createdAt: String? = null,
)

/**
 * A document file (PDF, text, etc.) attached to a chat message.
 */
@Serializable
data class MessageDocumentFile(
    @SerialName("file_uuid") val fileUuid: String,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("sanitized_name") val sanitizedName: String? = null,
    val path: String? = null,
    @SerialName("size_bytes") val sizeBytes: Long? = null,
    @SerialName("document_asset") val documentAsset: JsonElement? = null,
    @SerialName("thumbnail_asset") val thumbnailAsset: JsonElement? = null,
    @SerialName("created_at") val createdAt: String? = null,
)

/**
 * Request body for recording a user's tool approval decision.
 */
@Serializable
data class RecordToolApprovalRequest(
    @SerialName("tool_use_id") val toolUseId: String,
    @SerialName("approval_key") val approvalKey: String? = null,
    @SerialName("approval_option") val approvalOption: String? = null,
    @SerialName("is_approved") val isApproved: Boolean,
    val params: JsonElement? = null,
)

/**
 * Navigation params for the main chat screen.
 */
data class ChatScreenParams(
    val chatId: String? = null,
    val projectId: String? = null,
    val initialPrompt: String? = null,
    val initialModel: String? = null,
    val isRootScreen: Boolean = false,
    val createdAsNewChat: Boolean = false,
    val artifactIdentifierToOpen: String? = null,
    val orbitActionUuid: String? = null,
)
