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
 * Minimal task summary — full model in TaskResponse.kt.
 * Used as list item to avoid circular references.
 */
@Serializable
data class TaskResponse(
    val uuid: String? = null,
    val title: String? = null,
    val status: TaskStatus? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
)
