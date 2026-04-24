package com.anthropic.claude.api.tasks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * An event logged when the model invokes a tool via an agent during task execution.
 */
@Serializable
data class AgentToolUseEvent(
    @SerialName("tool_use_id") val toolUseId: String,
    @SerialName("tool_name") val toolName: String? = null,
    val input: JsonElement? = null,
)
