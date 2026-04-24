package com.anthropic.claude.api.feature

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enum of known Claude feature flags.
 */
@Serializable
enum class Feature {
    @SerialName("unknown")          UNKNOWN,
    @SerialName("chat")             CHAT,
    @SerialName("web_search")       WEB_SEARCH,
    @SerialName("claude_code_web")  CLAUDE_CODE_WEB,
    @SerialName("WIGGLE")           WIGGLE,
    @SerialName("DITTOS")           DITTOS,
    @SerialName("SAFFRON")          SAFFRON,
}
