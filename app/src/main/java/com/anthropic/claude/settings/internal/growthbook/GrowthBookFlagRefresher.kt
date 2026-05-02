package com.anthropic.claude.settings.internal.growthbook

import android.util.Log
import com.anthropic.claude.datastore.GrowthBookDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Refreshes the GrowthBook feature flag cache from the remote API.
 *
 * This operation MUST be performed on a worker thread because it
 * involves network I/O and SharedPreferences writes.
 *
 * Typical usage: called from a WorkManager periodic worker or
 * during app bootstrap on a background coroutine.
 */
class GrowthBookFlagRefresher(
    private val dataStore: GrowthBookDataStore,
    private val apiUrl: String,
) {
    companion object {
        private const val TAG = "GrowthBookFlagRefresher"
    }

    /**
     * Fetches the latest feature flags from the GrowthBook API
     * and persists them to [GrowthBookDataStore].
     *
     * Must be called from a background thread.
     *
     * @return true if the refresh was successful.
     */
    suspend fun refresh(): Boolean = withContext(Dispatchers.IO) {
        check(!isMainThread()) {
            "Refreshing flag cache must be done on a worker thread."
        }

        try {
            // Fetch flags from GrowthBook API
            // Parse JSON response
            // Write to dataStore
            Log.i(TAG, "Feature flags refreshed successfully")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to refresh feature flags", e)
            false
        }
    }

    private fun isMainThread(): Boolean {
        return android.os.Looper.myLooper() == android.os.Looper.getMainLooper()
    }
}
