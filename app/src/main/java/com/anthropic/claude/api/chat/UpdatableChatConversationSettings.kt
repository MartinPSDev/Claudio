package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.McpToolKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Mutable settings for a chat conversation, sent as part of PATCH requests.
 *
 * Feature flags use internal Anthropic codenames:
 * - **bananagrams** — Google Workspace / calendar integration
 * - **foccacia** — unknown internal feature flag
 * - **sourdough** — unknown internal feature flag
 * - **monkeys_in_a_barrel** — unknown internal feature flag
 * - **compass_mode** — Research Mode (extended thinking + web search combo)
 * - **paprika_mode** — Extended Thinking mode
 * - **tool_search_mode** — controls tool-use search strategy
 */
@Serializable
data class UpdatableChatConversationSettings(
    @SerialName("enabled_bananagrams") val enabledBananagrams: Boolean? = null,
    @SerialName("enabled_mcp_tools") val enabledMcpTools: Map<McpToolKey, Boolean>? = null,
    @SerialName("enabled_foccacia") val enabledFoccacia: Boolean? = null,
    @SerialName("enabled_sourdough") val enabledSourdough: Boolean? = null,
    @SerialName("enabled_web_search") val enabledWebSearch: Boolean? = null,
    @SerialName("enabled_monkeys_in_a_barrel") val enabledMonkeysInABarrel: Boolean? = null,
    @SerialName("compass_mode") val compassMode: String? = null,
    @SerialName("paprika_mode") val paprikaMode: String? = null,
    @SerialName("tool_search_mode") val toolSearchMode: String? = null,
)
