package com.anthropic.claude.app.main.loggedout

/**
 * Navigation destinations for the logged-out app state.
 */
sealed interface LoggedOutAppDestination {
    /** Navigate to the login flow. */
    data object LoginApp : LoggedOutAppDestination
}
