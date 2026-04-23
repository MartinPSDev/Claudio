package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Per-conversation tool and feature settings.
 * Controls which tools and experimental features are active for a conversation.
 */
@Serializable
data class ChatConversationSettings(
    @SerialName("enabled_bananagrams") val enabledBananagrams: Boolean? = null,
    @SerialName("enabled_mcp_tools") val enabledMcpTools: Boolean? = null,
    @SerialName("enabled_web_search") val enabledWebSearch: Boolean? = null,
    @SerialName("enabled_sourdough") val enabledSourdough: Boolean? = null,
    @SerialName("enabled_turmeric") val enabledTurmeric: Boolean? = null,
    @SerialName("enabled_foccacia") val enabledFoccacia: Boolean? = null,
    @SerialName("enabled_monkeys_in_a_barrel") val enabledMonkeysInABarrel: Boolean? = null,
    @SerialName("preview_feature_uses_artifacts") val previewFeatureUsesArtifacts: Boolean? = null,
    @SerialName("compass_mode") val compassMode: String? = null,
    @SerialName("paprika_mode") val paprikaMode: String? = null,
    @SerialName("tool_search_mode") val toolSearchMode: String? = null,
)
