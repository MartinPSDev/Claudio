package com.anthropic.claude.api.tasks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Lifecycle states for a background agent task.
 * [isActive] returns true for states where the task is still running or waiting.
 */
@Serializable
enum class TaskStatus(val value: String) {
    @SerialName("proposed") PROPOSED("proposed"),
    @SerialName("active") ACTIVE("active"),
    @SerialName("needs_attention") NEEDS_ATTENTION("needs_attention"),
    @SerialName("paused") PAUSED("paused"),
    @SerialName("completed") COMPLETED("completed"),
    @SerialName("failed") FAILED("failed"),
    @SerialName("rejected") REJECTED("rejected");

    val isActive: Boolean
        get() = this == ACTIVE || this == NEEDS_ATTENTION || this == PROPOSED
}

/**
 * Pagination cursor container for the tasks list endpoint.
 */
@Serializable
data class TasksPagination(
    @SerialName("next_cursor") val nextCursor: String? = null,
    @SerialName("has_more") val hasMore: Boolean = false,
)

/**
 * Paginated list of task responses.
 */
@Serializable
data class PaginatedTasksResponse(
    val tasks: List<TaskResponse>? = null,
    val pagination: TasksPagination? = null,
)

/**
 * A context attachment (file/document) associated with a task.
 */
@Serializable
data class TaskContextAttachment(
    val uuid: String? = null,
    val name: String? = null,
    val type: String? = null,
)

/**
 * A single step within a task, tracking granular progress.
 * 11 fields (42 KB).
 */
@Serializable
data class TaskStepResponse(
    val uuid: String? = null,
    val position: Int = 0,
    val title: String? = null,
    val notes: String? = null,
    val status: String? = null,
    @SerialName("status_description") val statusDescription: String? = null,
    @SerialName("blocking_tool") val blockingTool: BlockingToolDetail? = null,
    @SerialName("sub_items_completed") val subItemsCompleted: Int = 0,
    @SerialName("sub_items_total") val subItemsTotal: Int = 0,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
)

/**
 * Full task record. 12 fields (54 KB).
 */
@Serializable
data class TaskResponse(
    val uuid: String? = null,
    @SerialName("conversation_uuid") val conversationUuid: String? = null,
    val title: String? = null,
    val status: TaskStatus? = null,
    @SerialName("status_description") val statusDescription: String? = null,
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("last_activity_at") val lastActivityAt: String? = null,
    val context: String? = null,
    @SerialName("context_attachments") val contextAttachments: List<TaskContextAttachment>? = null,
    val steps: List<TaskStepResponse>? = null,
)

/**
 * Response from the task events endpoint (streaming event log for a task).
 */
@Serializable
data class TaskEventsResponse(
    val data: List<kotlinx.serialization.json.JsonElement>? = null,
    val pagination: TasksPagination? = null,
)
