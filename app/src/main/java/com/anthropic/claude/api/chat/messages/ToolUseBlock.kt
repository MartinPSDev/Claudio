package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A tool use block within a chat message — represents a tool call
 * made by the model, including its display state and approval metadata.
 */
@Serializable
data class ToolUseBlock(
    val id: String,
    val name: String,
    val input: JsonElement? = null,
    @SerialName("start_timestamp") val startTimestamp: Long? = null,
    @SerialName("stop_timestamp") val stopTimestamp: Long? = null,
    @SerialName("display_content") val displayContent: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("icon_name") val iconName: String? = null,
    @SerialName("integration_name") val integrationName: String? = null,
    @SerialName("integration_icon_url") val integrationIconUrl: String? = null,
    @SerialName("is_mcp_app") val isMcpApp: Boolean = false,
    @SerialName("flags") val flags: Int = 0,
    @SerialName("approval_key") val approvalKey: String? = null,
    @SerialName("approval_options") val approvalOptions: JsonElement? = null,
)
