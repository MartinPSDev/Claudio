package com.anthropic.claude.application

import android.app.Application
import android.util.Log
import com.anthropic.claude.app.notifications.NotificationChannels
import com.anthropic.claude.core.telemetry.SentryInitializer
import com.anthropic.claude.di.AppContainer

/**
 * Application entry point. Initializes all core systems in the correct order:
 *
 * 1. DI Container (AppContainer)
 * 2. Sentry error reporting
 * 3. Notification channels
 * 4. Firebase (auto-initialized via google-services plugin)
 * 5. GrowthBook feature flags (via DataStore, lazily)
 *
 * Note: Firebase is auto-initialized by the google-services plugin and
 * ContentProvider-based init. Segment analytics is initialized lazily
 * on first login via SessionRepository.
 */
class ClaudeApplication : Application() {

    /** Global DI container — accessible from Activities and Services. */
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        // ── 1. DI Container ──────────────────────────────────────────────────
        container = AppContainer(this)
        Log.i(TAG, "AppContainer initialized")

        // ── 2. Sentry ────────────────────────────────────────────────────────
        initSentry()

        // ── 3. Notification Channels ─────────────────────────────────────────
        initNotificationChannels()

        // ── 4. Theme ─────────────────────────────────────────────────────────
        applyTheme()

        Log.i(TAG, "ClaudeApplication initialized")
    }

    // ── Sentry ───────────────────────────────────────────────────────────────

    private fun initSentry() {
        try {
            SentryInitializer.init(this, container.sentryBeforeSendFilter)
            Log.i(TAG, "Sentry initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Sentry", e)
        }
    }

    // ── Notification Channels ────────────────────────────────────────────────

    /**
     * Reads feature flags from GrowthBook DataStore to determine which
     * conditional notification channels to register.
     *
     * On first launch before flags are fetched, all conditional channels
     * default to disabled — they'll be created on next app start after
     * the flags sync.
     */
    private fun initNotificationChannels() {
        try {
            val flagProvider = appContainer.featureFlagProvider
            val flags = NotificationChannels.ChannelFlags(
                isBogosortEnabled = flagProvider.getBoolean(
                    com.anthropic.claude.configs.GrowthBookFeatureFlagProvider.FLAG_BOGOSORT_ENABLED
                ),
                isDispatchEnabled = flagProvider.getBoolean(
                    com.anthropic.claude.configs.GrowthBookFeatureFlagProvider.FLAG_DISPATCH_ENABLED
                ),
                isCompletionEnabled = flagProvider.getBoolean(
                    com.anthropic.claude.configs.GrowthBookFeatureFlagProvider.FLAG_COMPLETION_ENABLED
                ),
                isMarketingEnabled = flagProvider.getBoolean(
                    com.anthropic.claude.configs.GrowthBookFeatureFlagProvider.FLAG_MARKETING_ENABLED
                ),
            )
            NotificationChannels.createAll(this, flags)
            Log.i(TAG, "Notification channels registered")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to register notification channels", e)
        }
    }

    // ── Theme ────────────────────────────────────────────────────────────────

    /**
     * Applies the user's preferred theme mode (light/dark/system).
     * Reads the value from [InternalPreferencesStore.themeColorMode].
     */
    private fun applyTheme() {
        val mode = container.internalPreferencesStore.themeColorMode
        val nightMode = when (mode) {
            "dark"  -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
            "light" -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
            else    -> androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(nightMode)
        Log.i(TAG, "Theme applied: $mode → nightMode=$nightMode")
    }

    companion object {
        private const val TAG = "ClaudeApplication"
    }
}
