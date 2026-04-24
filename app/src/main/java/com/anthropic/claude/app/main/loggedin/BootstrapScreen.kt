package com.anthropic.claude.app.main.loggedin

/**
 * Navigation states for the bootstrap loading screen shown at startup.
 */
sealed interface BootstrapScreen {

    /**
     * Still loading account/org data needed to enter the app.
     */
    data class NeedsBootstrap(
        val organizationId: String? = null,
        val ageSignalsResult: String? = null,
    ) : BootstrapScreen

    /**
     * Bootstrap complete — ready to show the main logged-in shell.
     */
    data class Bootstrapped(
        val organizationId: String? = null,
        val ageSignalsResult: String? = null,
    ) : BootstrapScreen
}
