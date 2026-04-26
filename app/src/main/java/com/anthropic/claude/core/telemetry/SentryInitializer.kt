package com.anthropic.claude.core.telemetry

import android.content.Context
import androidx.credentials.exceptions.GetCredentialCancellationException
import io.sentry.android.core.SentryAndroid
import io.sentry.android.core.SentryAndroidOptions
import javax.net.ssl.SSLHandshakeException

/**
 * Initializes Sentry with the project's configuration.
 * The actual DSN is injected via BuildConfig — see local.properties for the value.
 *
 * Configuration extracted from the app (method `c` in the Sentry options callback):
 *   - Auto session tracking: enabled
 *   - Session tracking interval: 4 minutes
 *   - Screenshot attachment: disabled
 *   - View hierarchy attachment: enabled
 *   - Traces sample rate: 0.005 (0.5%)
 *   - Propagate traceparent: enabled
 *   - Idle timeout: 6 minutes
 *   - Deadline timeout: 30 seconds
 *   - PerformanceV2: enabled
 *   - App start profiling: enabled
 *   - Time to full display tracing: enabled
 *   - Tombstone: enabled
 *   - Ignored exceptions: GetCredentialCancellationException, SSLHandshakeException
 */
object SentryInitializer {

    private const val TRACES_SAMPLE_RATE = 0.005

    fun init(context: Context, beforeSendFilter: SentryBeforeSendFilter) {
        SentryAndroid.init(context) { options: SentryAndroidOptions ->
            options.dsn = BuildConfig.SENTRY_DSN

            // Session tracking
            options.isEnableAutoSessionTracking = true
            options.sessionTrackingIntervalMillis = 4 * 60 * 1000L   // 4 min

            // Screenshots / view hierarchy
            options.isAttachScreenshot = false
            options.isAttachViewHierarchy = true

            // Performance
            options.tracesSampleRate = TRACES_SAMPLE_RATE
            options.isPropagateTraceparent = true
            options.idleTimeout = 6 * 60 * 1000L                      // 6 min
            options.deadlineTimeout = 30 * 1000L                       // 30 sec
            options.isEnablePerformanceV2 = true
            options.isEnableAppStartProfiling = true
            options.isEnableTimeToFullDisplayTracing = true
            options.isTombstoneEnabled = true

            // Ignored exceptions — not reported to Sentry
            options.addIgnoredExceptionForType(GetCredentialCancellationException::class.java)
            options.addIgnoredExceptionForType(SSLHandshakeException::class.java)

            // Custom filters
            options.beforeSend = beforeSendFilter
        }
    }
}
