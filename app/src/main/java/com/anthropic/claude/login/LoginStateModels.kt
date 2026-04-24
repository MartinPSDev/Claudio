package com.anthropic.claude.login

/** Extended login screen states. */
sealed interface LoginScreensExtended {
    /** Welcome screen, optionally shown after an API auth error. */
    data class Welcome(val showingAfterApiAuthError: Boolean = false) : LoginScreensExtended
}

/** Logged-out state for the main app shell. */
data class MainAppLoggedOut(
    val fromApiAuthError: Boolean = false,
)
