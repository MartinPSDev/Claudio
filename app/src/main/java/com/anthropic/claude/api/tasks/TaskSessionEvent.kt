package com.anthropic.claude.api.tasks

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** An event emitted during a task session (tool use, message, etc.). */
@Serializable
data class TaskSessionEvent(
    val type: String? = null,
    val data: JsonElement? = null,
)
