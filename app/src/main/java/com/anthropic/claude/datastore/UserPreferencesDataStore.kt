package com.anthropic.claude.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Jetpack DataStore extension on Context — single instance per app. */
private val Context.userPreferencesStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences",
)

/**
 * Manages user-level preferences: onboarding completion, theme, default model,
 * sticky style selection, and feature flag overrides.
 */
class UserPreferencesDataStore(private val context: Context) {

    private val store: DataStore<Preferences> get() = context.userPreferencesStore

    // ── Keys ─────────────────────────────────────────────────────────────────

    companion object {
        val KEY_ONBOARDING_COMPLETE  = booleanPreferencesKey("onboarding_complete")
        val KEY_DARK_MODE_OVERRIDE   = booleanPreferencesKey("dark_mode_override")
        val KEY_SYSTEM_THEME         = booleanPreferencesKey("use_system_theme")
        val KEY_DEFAULT_MODEL_ID     = stringPreferencesKey("default_model_id")
        val KEY_STICKY_STYLE_ID      = stringPreferencesKey("sticky_style_id")
        val KEY_THINKING_MODE        = stringPreferencesKey("thinking_mode")
        val KEY_LAST_ORG_ID          = stringPreferencesKey("last_organization_id")
        val KEY_NOTIFICATION_BADGE   = booleanPreferencesKey("notification_badge_enabled")
        val KEY_APP_LAUNCH_COUNT     = intPreferencesKey("app_launch_count")
        val KEY_AGE_SIGNALS_RESULT   = stringPreferencesKey("age_signals_result")
        val KEY_STT_LANGUAGE         = stringPreferencesKey("stt_language_code")
    }

    // ── Observe ───────────────────────────────────────────────────────────────

    val isOnboardingComplete: Flow<Boolean> = store.data
        .map { it[KEY_ONBOARDING_COMPLETE] ?: false }

    val isDarkModeOverride: Flow<Boolean> = store.data
        .map { it[KEY_DARK_MODE_OVERRIDE] ?: false }

    val useSystemTheme: Flow<Boolean> = store.data
        .map { it[KEY_SYSTEM_THEME] ?: true }

    val defaultModelId: Flow<String?> = store.data
        .map { it[KEY_DEFAULT_MODEL_ID] }

    val stickyStyleId: Flow<String?> = store.data
        .map { it[KEY_STICKY_STYLE_ID] }

    val thinkingMode: Flow<String?> = store.data
        .map { it[KEY_THINKING_MODE] }

    val lastOrganizationId: Flow<String?> = store.data
        .map { it[KEY_LAST_ORG_ID] }

    val notificationBadgeEnabled: Flow<Boolean> = store.data
        .map { it[KEY_NOTIFICATION_BADGE] ?: true }

    val appLaunchCount: Flow<Int> = store.data
        .map { it[KEY_APP_LAUNCH_COUNT] ?: 0 }

    val ageSignalsResult: Flow<String?> = store.data
        .map { it[KEY_AGE_SIGNALS_RESULT] }

    val sttLanguageCode: Flow<String?> = store.data
        .map { it[KEY_STT_LANGUAGE] }

    // ── Write ─────────────────────────────────────────────────────────────────

    suspend fun setOnboardingComplete(complete: Boolean) =
        store.edit { it[KEY_ONBOARDING_COMPLETE] = complete }

    suspend fun setDarkMode(dark: Boolean) =
        store.edit { it[KEY_DARK_MODE_OVERRIDE] = dark }

    suspend fun setUseSystemTheme(useSystem: Boolean) =
        store.edit { it[KEY_SYSTEM_THEME] = useSystem }

    suspend fun setDefaultModelId(modelId: String?) =
        store.edit {
            if (modelId == null) it.remove(KEY_DEFAULT_MODEL_ID)
            else it[KEY_DEFAULT_MODEL_ID] = modelId
        }

    suspend fun setStickyStyleId(styleId: String?) =
        store.edit {
            if (styleId == null) it.remove(KEY_STICKY_STYLE_ID)
            else it[KEY_STICKY_STYLE_ID] = styleId
        }

    suspend fun setThinkingMode(mode: String?) =
        store.edit {
            if (mode == null) it.remove(KEY_THINKING_MODE)
            else it[KEY_THINKING_MODE] = mode
        }

    suspend fun setLastOrganizationId(orgId: String) =
        store.edit { it[KEY_LAST_ORG_ID] = orgId }

    suspend fun setNotificationBadgeEnabled(enabled: Boolean) =
        store.edit { it[KEY_NOTIFICATION_BADGE] = enabled }

    suspend fun incrementLaunchCount() =
        store.edit { it[KEY_APP_LAUNCH_COUNT] = (it[KEY_APP_LAUNCH_COUNT] ?: 0) + 1 }

    suspend fun setAgeSignalsResult(result: String) =
        store.edit { it[KEY_AGE_SIGNALS_RESULT] = result }

    suspend fun setSttLanguage(code: String) =
        store.edit { it[KEY_STT_LANGUAGE] = code }

    suspend fun clearAll() = store.edit { it.clear() }
}
