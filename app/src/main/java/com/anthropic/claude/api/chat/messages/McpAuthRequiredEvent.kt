package com.anthropic.claude.api.chat.messages

import com.anthropic.claude.types.strings.McpServerId
import com.anthropic.claude.types.strings.ToolUseId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class McpAuthRequiredEvent(
    @SerialName("server_id") val serverId: McpServerId,
    @SerialName("tool_use_id") val toolUseId: ToolUseId,
) : StreamEvent
