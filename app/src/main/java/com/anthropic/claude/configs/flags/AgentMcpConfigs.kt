package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Debug-only overrides for the task agent model and configuration.
 * Only active in internal/development builds.
 */
@Serializable
data class TaskAgentOverridesDebugConfig(
    val models: JsonElement? = null,
)

/**
 * Conditionally enables MCP directory servers based on visibility rules.
 */
@Serializable
data class ConditionalMcpDirectoryServersConfig(
    val visibility: JsonElement? = null,
)
