package com.anthropic.claude.app.main.loggedin

/**
 * Screens shown to logged-in users.
 */
sealed interface LoggedInScreens {
    /** The main Claude app shell. */
    data object ClaudeApp : LoggedInScreens
    /** Onboarding flow for new users. */
    data object Onboarding : LoggedInScreens
    /** Age / identity verification gate. */
    data object AccountVerification : LoggedInScreens
    /** Blocked state for supervised (child) accounts. */
    data object SupervisedUserBlocked : LoggedInScreens
}
