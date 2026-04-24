package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Input for the message-compose tool (v0).
 */
@Serializable
data class MessageComposeV0Input(
    val body: String? = null,
    val kind: String? = null,
    val subject: String? = null,
    @SerialName("summary_title") val summaryTitle: String? = null,
)

/**
 * Input for the task-propose tool.
 */
@Serializable
data class TaskProposeInput(
    val context: String? = null,
    val title: String? = null,
    val steps: List<JsonElement>? = null,
)
