package com.anthropic.claude.configs

import com.anthropic.claude.deeplink.FeatureFlagProvider
import com.anthropic.claude.datastore.GrowthBookDataStore

/**
 * Production implementation of [FeatureFlagProvider] backed by GrowthBook.
 *
 * Reads cached feature flag values from [GrowthBookDataStore].
 * Falls back to `false` if the flag is not present or not yet synced.
 */
class GrowthBookFeatureFlagProvider(
    private val dataStore: GrowthBookDataStore,
) : FeatureFlagProvider {

    /**
     * Returns the boolean value of the given feature flag.
     * Returns `false` if the flag is unknown or not yet synced.
     */
    override fun getBoolean(key: String): Boolean {
        return dataStore.getBoolean(key, defaultValue = false)
    }

    /**
     * Returns the string value of a feature flag, or null.
     */
    fun getString(key: String): String? {
        return dataStore.getString(key)
    }

    /**
     * Returns the integer value of a feature flag, or the provided default.
     */
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return dataStore.getInt(key, defaultValue)
    }

    companion object {
        // Known feature flag keys used across the app
        const val FLAG_MOBILE_ARTIFACT_DEEPLINK_KILL_SWITCH = "mobile_artifact_deep_link_kill_switch"
        const val FLAG_BOGOSORT_ENABLED = "bogosort_notifications_enabled"
        const val FLAG_DISPATCH_ENABLED = "dispatch_notifications_enabled"
        const val FLAG_COMPLETION_ENABLED = "completion_notifications_enabled"
        const val FLAG_MARKETING_ENABLED = "marketing_notifications_enabled"
        const val FLAG_SESSION_REPLAY_SAMPLE_RATE = "session_replay_sample_rate"
        const val FLAG_OKHTTP_INTERCEPTOR_SAMPLE_RATE = "okhttp_interceptor_sample_rate"
    }
}
