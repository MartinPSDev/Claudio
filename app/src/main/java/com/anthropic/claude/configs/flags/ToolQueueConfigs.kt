package com.anthropic.claude.configs.flags

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Configuration for tool search behavior and available modes.
 */
@Serializable
data class ToolSearchConfig(
    val modes: JsonElement? = null,
)

/**
 * Configuration for the message queue — controls TTL and concurrency.
 */
@Serializable
data class MessageQueueConfig(
    @kotlinx.serialization.SerialName("ttl_hours") val ttlHours: Int = 24,
    @kotlinx.serialization.SerialName("max_active") val maxActive: Int = 5,
)
