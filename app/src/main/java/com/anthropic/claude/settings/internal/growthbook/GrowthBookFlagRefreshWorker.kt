package com.anthropic.claude.settings.internal.growthbook

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.anthropic.claude.datastore.GrowthBookDataStore
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Periodic WorkManager worker that refreshes the GrowthBook feature flag cache
 * from the Anthropic API.
 *
 * Runs every 4 hours in the background to keep feature flags up to date.
 * The flags are stored in [GrowthBookDataStore] and consumed by
 * [GrowthBookFeatureFlagProvider].
 */
class GrowthBookFlagRefreshWorker(
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    companion object {
        private const val TAG = "GrowthBookFlagRefresh"
        private const val WORK_NAME = "growthbook_flag_refresh"
        private const val REFRESH_INTERVAL_HOURS = 4L
        private const val FLAGS_ENDPOINT = "/api/bootstrap/flags"

        /**
         * Enqueues the periodic flag refresh worker.
         * Uses [ExistingPeriodicWorkPolicy.KEEP] to avoid duplicate workers.
         */
        fun enqueue(context: Context) {
            val request = PeriodicWorkRequestBuilder<GrowthBookFlagRefreshWorker>(
                REFRESH_INTERVAL_HOURS, TimeUnit.HOURS,
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request,
            )
        }

        /**
         * Cancels the periodic flag refresh worker.
         */
        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Refreshing flag cache")

        return try {
            val httpClient = OkHttpClient()
            val request = Request.Builder()
                .url("https://claude.ai$FLAGS_ENDPOINT")
                .get()
                .build()

            val response = httpClient.newCall(request).execute()

            if (!response.isSuccessful) {
                Log.w(TAG, "Flag refresh failed: ${response.code}")
                return Result.retry()
            }

            val body = response.body?.string() ?: return Result.retry()
            val flags = json.decodeFromString<JsonObject>(body)

            val dataStore = GrowthBookDataStore(applicationContext)
            dataStore.saveFlags(flags.toString())

            Log.i(TAG, "Flag cache refreshed successfully (${flags.size} flags)")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Flag refresh error", e)
            Result.retry()
        }
    }
}
