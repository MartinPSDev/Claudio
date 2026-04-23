package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The proposed task plan output returned by the task-propose tool.
 */
@Serializable
data class TaskProposeOutput(
    val uuid: String? = null,
    val title: String? = null,
    val context: String? = null,
    val status: String? = null,
    @SerialName("status_description") val statusDescription: String? = null,
    val steps: List<JsonElement>? = null,
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("last_activity_at") val lastActivityAt: String? = null,
)
