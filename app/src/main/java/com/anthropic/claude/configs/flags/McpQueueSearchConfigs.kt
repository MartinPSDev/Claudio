package com.anthropic.claude.configs.flags

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Remote-config: conditional MCP directory server visibility. */
@Serializable
data class ConditionalMcpDirectoryServersConfig(
    val visibility: String? = null,
)

/** Remote-config: message queue limits and TTL. */
@Serializable
data class MessageQueueConfig(
    val ttl_hours: Int? = null,
    val max_active: Int? = null,
)

/** Debug-only config for overriding task agent model selection. */
@Serializable
data class TaskAgentOverridesDebugConfig(
    val models: JsonElement? = null,
)

/** Remote-config: allowed search modes for the tool-search feature. */
@Serializable
data class ToolSearchConfig(
    val modes: List<String>? = null,
)
