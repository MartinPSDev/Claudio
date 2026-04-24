package com.anthropic.claude.analytics.events

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Analytics event container for age-signals checks.
 */
@Serializable
data class AgeSignalsEvents(
    val result: JsonElement? = null,
    val errorType: AgeSignalsErrorType? = null,
)

/**
 * Analytics event container for app-start metrics.
 */
@Serializable
data class AppStartEvents(
    val launchType: AppLaunchStartType? = null,
    val durationMs: Long? = null,
)
