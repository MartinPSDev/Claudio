package com.anthropic.claude.api.tasks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to approve a pending task action. */
@Serializable
data class ApproveTaskRequest(
    @SerialName("agent_overrides") val agentOverrides: JsonElement? = null,
)
