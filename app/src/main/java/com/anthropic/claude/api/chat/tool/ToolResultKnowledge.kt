package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Knowledge/grounding context returned in a tool result.
 */
@Serializable
data class ToolResultKnowledge(
    val title: String? = null,
    val url: String? = null,
    val metadata: JsonElement? = null,
)
