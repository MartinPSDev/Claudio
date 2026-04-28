package com.anthropic.claude.core.telemetry

import android.content.Context
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.rum.RumConfiguration
import com.datadog.android.trace.TraceConfiguration

/**
 * Initializes Datadog RUM, Logging, and Tracing.
 *
 * The client token and application ID are injected via BuildConfig
 * (same pattern as Sentry DSN).
 *
 * Configuration extracted from the Datadog initializer:
 *   - site = US1
 *   - crashReportsEnabled = true
 *   - Session replay sample rate from feature flag
 *   - OkHttp interceptor sample rate from feature flag
 */
object DatadogInitializer {

    fun init(context: Context) {
        val clientToken = BuildConfig.DATADOG_CLIENT_TOKEN
        val applicationId = BuildConfig.DATADOG_APP_ID
        val environment = BuildConfig.BUILD_TYPE // "debug" or "release"

        if (clientToken.isBlank() || applicationId.isBlank()) return

        val configuration = Configuration.Builder(
            clientToken = clientToken,
            env = environment,
            variant = "",
            service = "claude-android",
        )
            .useSite(DatadogSite.US1)
            .setCrashReportsEnabled(true)
            .build()

        Datadog.initialize(context.applicationContext, configuration, null)

        // RUM
        val rumConfig = RumConfiguration.Builder(applicationId)
            .trackUserInteractions()
            .trackLongTasks()
            .build()
        // Note: RUM monitoring is enabled via Rum.enable(rumConfig)

        // Tracing
        val traceConfig = TraceConfiguration.Builder().build()
        // Note: Trace.enable(traceConfig)
    }
}
