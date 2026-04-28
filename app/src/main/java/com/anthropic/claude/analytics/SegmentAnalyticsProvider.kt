package com.anthropic.claude.analytics

import android.content.Context
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics

/**
 * Initializes and provides a configured Segment Analytics instance.
 *
 * Anthropic proxies Segment traffic through their own infrastructure:
 *   - API Host: `a-api.anthropic.com/v1`
 *   - CDN Host: `a-cdn.anthropic.com/v1`
 *
 * Configuration:
 *   - collectDeviceId = false (privacy-conscious)
 *   - trackApplicationLifecycleEvents = true
 *   - trackDeepLinks = false (handled manually)
 *   - flushAt = 3 events
 *   - flushInterval = 10 seconds
 */
object SegmentAnalyticsProvider {

    private const val API_HOST = "a-api.anthropic.com/v1"
    private const val CDN_HOST = "a-cdn.anthropic.com/v1"
    private const val FLUSH_AT = 3
    private const val FLUSH_INTERVAL_SECONDS = 10

    @Volatile
    private var instance: Analytics? = null

    /**
     * Returns the singleton Segment Analytics instance.
     * Creates it on first call using the write key from BuildConfig.
     */
    fun get(context: Context): Analytics {
        return instance ?: synchronized(this) {
            instance ?: createAnalytics(context).also { instance = it }
        }
    }

    private fun createAnalytics(context: Context): Analytics {
        return Analytics(
            writeKey = BuildConfig.SEGMENT_WRITE_KEY,
            context = context.applicationContext,
        ) {
            apiHost(API_HOST)
            cdnHost(CDN_HOST)
            collectDeviceId = false
            trackApplicationLifecycleEvents = true
            trackDeepLinks = false
            flushAt = FLUSH_AT
            flushInterval = FLUSH_INTERVAL_SECONDS
        }
    }

    /**
     * Shuts down the analytics instance gracefully.
     * Call during application teardown or test cleanup.
     */
    fun shutdown() {
        synchronized(this) {
            instance?.flush()
            instance = null
        }
    }
}
