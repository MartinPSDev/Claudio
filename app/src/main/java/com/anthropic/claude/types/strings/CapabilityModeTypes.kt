package com.anthropic.claude.types.strings

import kotlinx.serialization.Serializable

/**
 * Inline value class for a capability flag string.
 * Known values: "chat", "claude_pro", "claude_max", "raven".
 */
@Serializable
@JvmInline
value class Capability(val value: String)

/**
 * Tool search mode inline class.
 * Known values: "auto".
 */
@Serializable
@JvmInline
value class ToolSearchMode(val value: String)

/**
 * Research depth mode inline class.
 * Known values: "none", "light", "advanced".
 */
@Serializable
@JvmInline
value class ResearchMode(val value: String) {
    companion object {
        val NONE     = ResearchMode("none")
        val LIGHT    = ResearchMode("light")
        val ADVANCED = ResearchMode("advanced")
    }
}
