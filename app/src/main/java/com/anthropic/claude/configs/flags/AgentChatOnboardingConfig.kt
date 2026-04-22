package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentChatOnboardingConfig(
    @SerialName("desktop_download_url") val desktopDownloadUrl: String? = null,
) {
    companion object {
        const val CONFIG_NAME = "mobile_cowork_onboarding_config"
        const val DEFAULT_DESKTOP_DOWNLOAD_URL = "https://claude.ai/download"
    }
}
