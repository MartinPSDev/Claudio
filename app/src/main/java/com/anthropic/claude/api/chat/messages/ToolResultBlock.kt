package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A tool result block — the response returned after a tool call completes.
 */
@Serializable
data class ToolResultBlock(
    val id: String? = null,
    val name: String? = null,
    @SerialName("start_timestamp") val startTimestamp: Long? = null,
    @SerialName("stop_timestamp") val stopTimestamp: Long? = null,
    val content: JsonElement? = null,
    @SerialName("structured_content") val structuredContent: JsonElement? = null,
    @SerialName("display_content") val displayContent: String? = null,
    val message: String? = null,
    @SerialName("icon_name") val iconName: String? = null,
    @SerialName("integration_name") val integrationName: String? = null,
    @SerialName("integration_icon_url") val integrationIconUrl: String? = null,
    @SerialName("is_error") val isError: Boolean = false,
    val meta: JsonElement? = null,
    val flags: Int = 0,
)
