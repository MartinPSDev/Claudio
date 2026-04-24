package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A known (named) tool with its type identifier.
 */
@Serializable
data class KnownTool(
    val name: String? = null,
    val type: String? = null,
)
