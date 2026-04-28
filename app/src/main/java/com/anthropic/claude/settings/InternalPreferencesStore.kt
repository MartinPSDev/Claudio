package com.anthropic.claude.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit

/**
 * Internal developer/debug preferences store.
 * Wraps SharedPreferences with typed accessors for all internal configuration flags.
 */
class InternalPreferencesStore(
    private val prefs: SharedPreferences,
) {

    companion object {
        const val PREFS_NAME = "internal_preferences"

        // Preference keys
        const val KEY_MAGIC_LINK_PENDING      = "app_magic_link_pending_login"
        const val KEY_IS_ANT                  = "is_ant"
        const val KEY_API_BASE_URL            = "api_base_url"
        const val KEY_HTTP_LOGGING_VERBOSE    = "http_logging_verbose"
        const val KEY_JANK_OVERLAY_ENABLED    = "jank_overlay_enabled"
        const val KEY_HTTP_COUNTRY_OVERRIDE   = "http_override_country_code"
        const val KEY_STT_EDUCATION_SHOWN     = "stt_education_prompt_shown"
        const val KEY_STT_LANGUAGE            = "stt_language"
        const val KEY_THEME_COLOR_MODE        = "theme_color_mode"
        const val KEY_HAPTIC_FEEDBACK         = "haptic_feedback_enabled"
        const val KEY_VOICE_SHORTCUT_SHOWN    = "voice_shortcut_dialog_shown"
        const val KEY_HAS_LOGGED_IN_BEFORE    = "has_logged_in_before"
        const val KEY_FONT_STYLE              = "font_style"
        const val KEY_DEBUG_FORCE_UPGRADE     = "debug_force_upgrade_version"
        const val KEY_SLOW_NETWORK_SIMULATION = "slow_network_simulation_enabled"
        const val KEY_UPLOAD_FAILURE_RATE     = "upload_failure_rate"
        const val KEY_REQUEST_FAILURE_RATE    = "request_failure_rate"
        const val KEY_REQUEST_LATENCY_MS      = "request_latency_ms"
        const val KEY_LAST_SUBSCRIPTION_LEVEL = "last_subscription_level"
        const val KEY_INTERNAL_FORCE_OKHTTP   = "internal_force_okhttp"
        const val KEY_RECORD_SSE_TRANSCRIPTS  = "record_sse_transcripts"
        const val KEY_DEBUG_AGE_SIGNALS       = "debug_age_signals_override"
        const val KEY_HAS_LOGGED_INSTALL_REF  = "has_logged_install_referrer"
        const val KEY_CONWAY_BASE_URL         = "conway_base_url_override"
        const val KEY_CONWAY_SHOW_TOOL_CALLS  = "conway_show_tool_calls"
        const val KEY_LAST_VISITED_FEATURE    = "last_visited_feature"
        const val KEY_DEBUG_FORCE_FLEX_UPDATE = "debug_force_flexible_update"
        const val KEY_DEBUG_FORCE_OCTOPUS     = "debug_force_octopus"

        // Default values
        const val DEFAULT_COUNTRY_CODE        = "US"
        const val DEFAULT_THEME_COLOR_MODE    = "system"
        const val DEFAULT_FONT_STYLE          = "default"

        fun from(context: Context): InternalPreferencesStore =
            InternalPreferencesStore(
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE),
            )
    }

    // ── Read ─────────────────────────────────────────────────────────────────

    val isMagicLinkPendingLogin: Boolean
        get() = prefs.getBoolean(KEY_MAGIC_LINK_PENDING, false)

    val isAnt: Boolean
        get() = prefs.getBoolean(KEY_IS_ANT, false)

    /** Override base URL for pointing at staging or local servers. Null = production. */
    val apiBaseUrlOverride: String?
        get() = prefs.getString(KEY_API_BASE_URL, null)

    val isHttpLoggingVerbose: Boolean
        get() = prefs.getBoolean(KEY_HTTP_LOGGING_VERBOSE, false)

    val isJankOverlayEnabled: Boolean
        get() = prefs.getBoolean(KEY_JANK_OVERLAY_ENABLED, false)

    /** Country code override for HTTP requests (default: "US"). */
    val httpCountryCodeOverride: String
        get() = prefs.getString(KEY_HTTP_COUNTRY_OVERRIDE, DEFAULT_COUNTRY_CODE) ?: DEFAULT_COUNTRY_CODE

    val hasSttEducationPromptBeenShown: Boolean
        get() = prefs.getBoolean(KEY_STT_EDUCATION_SHOWN, false)

    /** Language code for speech-to-text (null = device default). */
    val sttLanguage: String?
        get() = prefs.getString(KEY_STT_LANGUAGE, null)

    /**
     * Theme color mode. One of: "system", "light", "dark".
     * Only applicable on Android 9+ (SDK 28+); older devices are forced to "light".
     */
    val themeColorMode: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            prefs.getString(KEY_THEME_COLOR_MODE, DEFAULT_THEME_COLOR_MODE) ?: DEFAULT_THEME_COLOR_MODE
        } else {
            "light"
        }

    /** Haptic feedback enabled (default: true). */
    val isHapticFeedbackEnabled: Boolean
        get() = prefs.getBoolean(KEY_HAPTIC_FEEDBACK, true)

    val hasVoiceShortcutDialogBeenShown: Boolean
        get() = prefs.getBoolean(KEY_VOICE_SHORTCUT_SHOWN, false)

    val hasLoggedInBefore: Boolean
        get() = prefs.getBoolean(KEY_HAS_LOGGED_IN_BEFORE, false)

    /** Font style. One of: "default", "serif", "monospace". */
    val fontStyle: String
        get() = prefs.getString(KEY_FONT_STYLE, DEFAULT_FONT_STYLE) ?: DEFAULT_FONT_STYLE

    /** Forces a specific version string for the upgrade dialog (debug only). */
    val debugForceUpgradeVersion: String?
        get() = prefs.getString(KEY_DEBUG_FORCE_UPGRADE, null)

    val isSlowNetworkSimulationEnabled: Boolean
        get() = prefs.getBoolean(KEY_SLOW_NETWORK_SIMULATION, false)

    val uploadFailureRate: Int
        get() = prefs.getInt(KEY_UPLOAD_FAILURE_RATE, 0)

    val requestFailureRate: Int
        get() = prefs.getInt(KEY_REQUEST_FAILURE_RATE, 0)

    val requestLatencyMs: Int
        get() = prefs.getInt(KEY_REQUEST_LATENCY_MS, 0)

    val lastSubscriptionLevel: String?
        get() = prefs.getString(KEY_LAST_SUBSCRIPTION_LEVEL, null)

    val isInternalForceOkHttp: Boolean
        get() = prefs.getBoolean(KEY_INTERNAL_FORCE_OKHTTP, false)

    val isRecordSseTranscriptsEnabled: Boolean
        get() = prefs.getBoolean(KEY_RECORD_SSE_TRANSCRIPTS, false)

    val debugAgeSignalsOverride: String?
        get() = prefs.getString(KEY_DEBUG_AGE_SIGNALS, null)

    val hasLoggedInstallReferrer: Boolean
        get() = prefs.getBoolean(KEY_HAS_LOGGED_INSTALL_REF, false)

    val conwayBaseUrlOverride: String?
        get() = prefs.getString(KEY_CONWAY_BASE_URL, null)

    val isConwayShowToolCallsEnabled: Boolean
        get() = prefs.getBoolean(KEY_CONWAY_SHOW_TOOL_CALLS, false)

    val lastVisitedFeature: String?
        get() = prefs.getString(KEY_LAST_VISITED_FEATURE, null)

    val isDebugForceFlexibleUpdate: Boolean
        get() = prefs.getBoolean(KEY_DEBUG_FORCE_FLEX_UPDATE, false)

    val isDebugForceOctopus: Boolean
        get() = prefs.getBoolean(KEY_DEBUG_FORCE_OCTOPUS, false)

    // ── Write ────────────────────────────────────────────────────────────────

    fun setMagicLinkPendingLogin(pending: Boolean) =
        prefs.edit { putBoolean(KEY_MAGIC_LINK_PENDING, pending) }

    fun setHasLoggedInBefore(value: Boolean) =
        prefs.edit { putBoolean(KEY_HAS_LOGGED_IN_BEFORE, value) }

    fun setHasSttEducationPromptShown(shown: Boolean) =
        prefs.edit { putBoolean(KEY_STT_EDUCATION_SHOWN, shown) }

    fun setHasVoiceShortcutDialogShown(shown: Boolean) =
        prefs.edit { putBoolean(KEY_VOICE_SHORTCUT_SHOWN, shown) }

    fun setThemeColorMode(mode: String) =
        prefs.edit { putString(KEY_THEME_COLOR_MODE, mode) }

    fun setFontStyle(style: String) =
        prefs.edit { putString(KEY_FONT_STYLE, style) }

    fun setLastSubscriptionLevel(level: String?) =
        prefs.edit { putString(KEY_LAST_SUBSCRIPTION_LEVEL, level) }

    fun setLastVisitedFeature(feature: String?) =
        prefs.edit { putString(KEY_LAST_VISITED_FEATURE, feature) }

    fun setHasLoggedInstallReferrer(logged: Boolean) =
        prefs.edit { putBoolean(KEY_HAS_LOGGED_INSTALL_REF, logged) }

    fun setApiBaseUrlOverride(url: String?) =
        prefs.edit {
            if (url != null) putString(KEY_API_BASE_URL, url) else remove(KEY_API_BASE_URL)
        }

    fun setConwayBaseUrlOverride(url: String?) =
        prefs.edit {
            if (url != null) putString(KEY_CONWAY_BASE_URL, url) else remove(KEY_CONWAY_BASE_URL)
        }

    // ── Debug simulation helpers ──────────────────────────────────────────────

    fun getRequestFailureRate(): Int = requestFailureRate

    fun getRequestLatencyMs(): Int = requestLatencyMs

    fun getUploadFailureRate(): Int = uploadFailureRate
}
