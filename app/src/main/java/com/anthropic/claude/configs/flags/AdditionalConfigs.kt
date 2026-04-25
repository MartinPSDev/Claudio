package com.anthropic.claude.configs.flags

import kotlinx.serialization.Serializable

/**
 * Configuration for the message queue system.
 */
@Serializable
data class MessageQueueConfig(
    val ttl_hours: Int = 24,
    val max_active: Int = 5
)

/**
 * Configuration for tool search.
 */
@Serializable
data class ToolSearchConfig(
    val enabled: Boolean = false,
    val max_results: Int = 10
)

/**
 * Debug configuration for task agent overrides.
 */
@Serializable
data class TaskAgentOverridesDebugConfig(
    val enabled: Boolean = false,
    val model_override: String? = null,
    val max_turns: Int? = null,
    val thinking_budget: Int? = null
)

/**
 * Conditional MCP directory servers configuration.
 */
@Serializable
data class ConditionalMcpDirectoryServersConfig(
    val enabled: Boolean = false,
    val server_ids: List<String>? = null,
    val conditions: List<String>? = null
)
