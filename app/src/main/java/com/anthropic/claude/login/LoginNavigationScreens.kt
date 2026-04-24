package com.anthropic.claude.login

import com.anthropic.claude.login.MagicLinkSentConfig

/** Login screen navigation states. */
sealed interface LoginScreens {
    /** Shown after a magic link email has been sent. */
    data class MagicLinkSent(val sentConfig: MagicLinkSentConfig? = null) : LoginScreens
}

/** Overlay screen navigation states (shown over login). */
sealed interface OverlayScreens {
    /** Verifying a magic link click from email. */
    data class MagicLinkVerify(val intentData: MagicLinkIntentData? = null) : OverlayScreens
}
