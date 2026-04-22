package com.anthropic.claude.app

import com.anthropic.claude.settings.SettingsAppScreen
import kotlinx.serialization.Serializable

/**
 * Navigation params used when launching the Settings screen via deep link or internal navigation.
 * [initialScreens] defines the back stack of settings screens to pre-load.
 * [deepLinkNonce] is a one-time token used to validate the deep link origin.
 */
@Serializable
data class SettingsScreenParams(
    val initialScreens: List<SettingsAppScreen> = emptyList(),
    val deepLinkNonce: Long = 0L,
)
