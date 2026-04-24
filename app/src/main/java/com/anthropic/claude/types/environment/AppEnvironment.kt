package com.anthropic.claude.types.environment

/**
 * The deployment environment the app is configured to run against.
 */
enum class AppEnvironment(val value: String) {
    PRODUCTION("production"),
    STAGING("staging"),
    DEVELOPMENT("DEVELOPMENT");

    companion object {
        fun fromValue(value: String): AppEnvironment =
            entries.firstOrNull { it.value == value } ?: PRODUCTION
    }
}
