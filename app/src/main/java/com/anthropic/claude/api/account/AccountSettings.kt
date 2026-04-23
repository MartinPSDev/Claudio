package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Per-account feature flags and UI state persisted server-side.
 * Controls which beta features and tools are enabled for the account.
 */
@Serializable
data class AccountSettings(
    // ─── Feature flags ──────────────────────────────────────────────────────
    @SerialName("enabled_web_search") val enabledWebSearch: Boolean? = null,
    @SerialName("enabled_mcp_tools") val enabledMcpTools: Boolean? = null,
    @SerialName("enabled_mm_pdfs") val enabledMmPdfs: Boolean? = null,
    @SerialName("enabled_saffron") val enabledSaffron: Boolean? = null,
    @SerialName("enabled_saffron_search") val enabledSaffronSearch: Boolean? = null,
    @SerialName("enabled_sourdough") val enabledSourdough: Boolean? = null,
    @SerialName("enabled_turmeric") val enabledTurmeric: Boolean? = null,
    @SerialName("enabled_foccacia") val enabledFoccacia: Boolean? = null,
    @SerialName("enabled_bananagrams") val enabledBananagrams: Boolean? = null,
    @SerialName("enabled_monkeys_in_a_barrel") val enabledMonkeysInABarrel: Boolean? = null,
    // ─── Grove (Projects v2) ────────────────────────────────────────────────
    @SerialName("grove_enabled") val groveEnabled: Boolean? = null,
    @SerialName("grove_notice_viewed_at") val groveNoticeViewedAt: String? = null,
    @SerialName("grove_updated_at") val groveUpdatedAt: String? = null,
    // ─── Dismissed UI banners ───────────────────────────────────────────────
    @SerialName("dismissed_claudeai_banners") val dismissedClaudeaiBanners: List<String>? = null,
    @SerialName("dismissed_artifacts_announcement") val dismissedArtifactsAnnouncement: Boolean? = null,
)
