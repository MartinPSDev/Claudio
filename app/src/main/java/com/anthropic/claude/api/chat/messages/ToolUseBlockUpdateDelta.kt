package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Streaming update delta for a tool-use block (approval flow + rich display). */
@Serializable
data class ToolUseBlockUpdateDelta(
    val message: String? = null,
    @SerialName("approval_key")      val approvalKey: String? = null,
    @SerialName("approval_options")  val approvalOptions: JsonElement? = null,
    @SerialName("display_content")   val displayContent: JsonElement? = null,
    @SerialName("icon_name")         val iconName: String? = null,
    @SerialName("integration_icon_url") val integrationIconUrl: String? = null,
    @SerialName("integration_name")  val integrationName: String? = null,
)
