package com.anthropic.claude.analytics

/**
 * Segment analytics user traits — sent on identify() calls.
 */
data class AnalyticsTraits(
    val accountUuid: String? = null,
    val email: String? = null,
    val organizationUuid: String? = null,
    val subscriptionLevel: String? = null,
)

/**
 * App-launch completed analytics event.
 * Fires after the first rendered frame post-launch.
 */
data class AppLaunchCompleted(
    val startType: String? = null,
    val durationMs: Long = 0L,
    val appOnCreateMs: Long = 0L,
) {
    companion object {
        const val EVENT_NAME = "mobile.app_start.launch_completed"
    }
}
