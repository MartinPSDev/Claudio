package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Server-provided localized strings for the Grove (Projects) onboarding and settings UI.
 */
@Serializable
data class GroveConfigStrings(
    @SerialName("disabled_toggle") val disabledToggle: String? = null,
    @SerialName("notice_title") val noticeTitle: String? = null,
    @SerialName("notice_description") val noticeDescription: String? = null,
    @SerialName("notice_footer") val noticeFooter: String? = null,
    @SerialName("notice_learnmore") val noticeLearnmore: String? = null,
    @SerialName("notice_p2_title") val noticeP2Title: String? = null,
    @SerialName("notice_p2_description") val noticeP2Description: String? = null,
    @SerialName("notice_toggle_title") val noticeToggleTitle: String? = null,
    @SerialName("notice_toggle_description") val noticeToggleDescription: String? = null,
    @SerialName("onboarding_bullet_title") val onboardingBulletTitle: String? = null,
    @SerialName("onboarding_bullet_description") val onboardingBulletDescription: String? = null,
    @SerialName("onboarding_toggle") val onboardingToggle: String? = null,
    @SerialName("settings_notice") val settingsNotice: String? = null,
    @SerialName("settings_toggle_title") val settingsToggleTitle: String? = null,
    @SerialName("settings_toggle_description") val settingsToggleDescription: String? = null,
)
