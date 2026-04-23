package com.anthropic.claude.api.share

import com.anthropic.claude.api.chat.ChatMessage
import com.anthropic.claude.types.strings.ChatSnapshotId
import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A public snapshot of a conversation, shared via a link.
 * Snapshots are immutable copies of a conversation at a point in time.
 */
@Serializable
data class ChatSnapshot(
    val uuid: ChatSnapshotId? = null,
    @SerialName("snapshot_name") val snapshotName: String? = null,
    @SerialName("is_public") val isPublic: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("project_uuid") val projectUuid: ProjectId? = null,
    val creator: ProjectCreator? = null,
    @SerialName("chat_messages") val chatMessages: List<ChatMessage>? = null,
)

/**
 * Minimal account info embedded in a shared snapshot.
 */
@Serializable
data class ProjectCreator(
    val uuid: String? = null,
    @SerialName("full_name") val fullName: String? = null,
)

/**
 * Summary of a snapshot used in list views (without full message payload).
 */
@Serializable
data class ChatSnapshotSummary(
    val uuid: ChatSnapshotId? = null,
    @SerialName("snapshot_name") val snapshotName: String? = null,
    @SerialName("is_public") val isPublic: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    val creator: ProjectCreator? = null,
)

/**
 * A snapshot summary linked to its parent conversation ID.
 */
@Serializable
data class ChatSnapshotSummaryWithChatId(
    val chatId: String? = null,
    val snapshot: ChatSnapshotSummary? = null,
)
