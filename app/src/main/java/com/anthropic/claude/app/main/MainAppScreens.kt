package com.anthropic.claude.app.main

/**
 * Sealed navigation destinations for the main logged-in app shell.
 */
sealed interface MainAppScreens {

    /**
     * The user is fully authenticated and onboarded.
     */
    data class LoggedIn(
        val accountId: String? = null,
        val initialOrganizationId: String? = null,
        val ageSignalsResult: String? = null,
    ) : MainAppScreens
}
