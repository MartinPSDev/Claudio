package com.anthropic.claude.login

/**
 * Extended login-screen navigation variants extracted from LoginScreens$* smali subclasses.
 */
sealed interface LoginScreensFull {
    /** Initial welcome/landing screen. May appear after an API auth error. */
    data class Welcome(val showingAfterApiAuthError: Boolean = false) : LoginScreensFull
    /** Magic link was sent — waiting for the user to check email. */
    data class MagicLinkSent(val sentConfig: MagicLinkSentConfig? = null) : LoginScreensFull
    /** Account is blocked (supervised / child account). */
    data object SupervisedUserBlocked : LoginScreensFull
}

/**
 * Overlay screens shown on top of login (e.g. deep-link callbacks).
 */
sealed interface OverlayScreensFull {
    /** No overlay. */
    data object None : OverlayScreensFull
    /** Verifying a magic link click from email. */
    data class MagicLinkVerify(val intentData: MagicLinkIntentData? = null) : OverlayScreensFull
}
