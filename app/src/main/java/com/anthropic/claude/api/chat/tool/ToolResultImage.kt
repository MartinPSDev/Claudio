package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** An image file attached to a tool result. */
@Serializable
data class ToolResultImage(
    @SerialName("file_uuid") val fileUuid: String? = null,
)
