package com.anthropic.claude.core.config

/**
 * Google OAuth client IDs for Google Sign-In / Credential Manager.
 * The actual values are injected via BuildConfig at compile time.
 *
 * Set the following in your local.properties or CI environment:
 *   GOOGLE_CLIENT_ID_PROD=...
 *   GOOGLE_CLIENT_ID_STAGING=...
 */
object GoogleClientIds {

    /** Production OAuth 2.0 client ID. */
    const val PRODUCTION_CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID_PROD

    /** Staging / Development OAuth 2.0 client ID. */
    const val STAGING_CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID_STAGING

    /**
     * Returns the correct client ID for the current environment.
     * @param isProduction true if running in production, false for staging/dev.
     */
    fun forEnvironment(isProduction: Boolean): String =
        if (isProduction) PRODUCTION_CLIENT_ID else STAGING_CLIENT_ID
}
