package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A text content block within a chat message.
 */
@Serializable
data class TextBlock(
    val text: String = "",
    @SerialName("start_timestamp") val startTimestamp: Long? = null,
    @SerialName("stop_timestamp") val stopTimestamp: Long? = null,
    val citations: JsonElement? = null,
    val flags: Int = 0,
)

/**
 * A streaming delta that updates an in-progress tool use block.
 * Received during SSE streaming before the tool call is complete.
 */
@Serializable
data class ToolUseBlockUpdateDelta(
    val message: String? = null,
    @SerialName("display_content") val displayContent: String? = null,
    @SerialName("icon_name") val iconName: String? = null,
    @SerialName("integration_name") val integrationName: String? = null,
    @SerialName("integration_icon_url") val integrationIconUrl: String? = null,
    @SerialName("approval_key") val approvalKey: String? = null,
    @SerialName("approval_options") val approvalOptions: JsonElement? = null,
)
