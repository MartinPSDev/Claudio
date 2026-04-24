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

    /** Logged-out state (covers fromApiAuthError coming from AuthExpiredInterceptor). */
    data class LoggedOut(val fromApiAuthError: Boolean = false) : MainAppScreens

    /** In-app internal debug/settings panel (debug builds only). */
    data object InternalSettings : MainAppScreens

    /** Forced-update gate — app is too old, user must update to continue. */
    data object RequiredUpdate   : MainAppScreens

    /** Add / switch account flow. */
    data object AddAccount       : MainAppScreens

    /** Internal UI demo / component showcase screen. */
    data object UiDemoApp        : MainAppScreens
}
