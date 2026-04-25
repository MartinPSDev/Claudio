package com.anthropic.claude.login

import kotlinx.serialization.Serializable

/**
 * Overlay screens shown during the login flow.
 *
 * Subtypes: None, MagicLinkVerify
 */
@Serializable
sealed interface OverlayScreens {

    /** No overlay is currently shown. */
    @Serializable
    data object None : OverlayScreens

    /** Magic link verification overlay. */
    @Serializable
    data class MagicLinkVerify(
        val email: String? = null
    ) : OverlayScreens
}
