package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingConfig(
    val pages: List<String> = emptyList(),
) {
    companion object {
        const val FEATURE_KEY = "mobile_new_onboarding_config"
    }
}
