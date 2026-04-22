package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration context injected into an agentic session.
 * Defines sources of context, working directory, outcomes, and system prompt overrides.
 */
@Serializable
data class SessionContext(
    val sources: List<SessionContextSource> = emptyList(),
    val cwd: String? = null,
    val outcomes: List<Outcome>? = null,
    @SerialName("custom_system_prompt") val customSystemPrompt: String? = null,
    @SerialName("append_system_prompt") val appendSystemPrompt: String? = null,
    val model: String? = null,
)

/** A single source of context fed into a session (file, URL, etc.). */
@Serializable
data class SessionContextSource(
    val type: String? = null,
    val content: String? = null,
    val uri: String? = null,
)

/** Expected outcome specification for an agentic session. */
@Serializable
data class Outcome(
    val type: String? = null,
    val description: String? = null,
)
