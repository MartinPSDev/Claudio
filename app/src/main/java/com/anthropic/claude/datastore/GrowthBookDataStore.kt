package com.anthropic.claude.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** DataStore for GrowthBook feature flag overrides (internal / debug settings). */
private val Context.growthBookStore: DataStore<Preferences> by preferencesDataStore(
    name = "growthbook_overrides",
)

/**
 * Stores GrowthBook feature flag debug overrides set from the internal settings screen.
 * Key pattern: "flag_<featureKey>" → overridden boolean value.
 */
class GrowthBookDataStore(private val context: Context) {

    private val store: DataStore<Preferences> get() = context.growthBookStore

    companion object {
        val KEY_DEBUG_ENABLED = booleanPreferencesKey("debug_overrides_enabled")

        /** Dynamic key factory for arbitrary feature flags. */
        fun flagKey(featureKey: String) = booleanPreferencesKey("flag_$featureKey")

        /** Dynamic key factory for string experiment overrides. */
        fun experimentKey(experimentId: String) = stringPreferencesKey("exp_$experimentId")
    }

    val debugOverridesEnabled: Flow<Boolean> = store.data
        .map { it[KEY_DEBUG_ENABLED] ?: false }

    /** Observe a single boolean flag override (null = not overridden). */
    fun observeFlag(featureKey: String): Flow<Boolean?> = store.data
        .map { it[flagKey(featureKey)] }

    /** Observe a single string experiment override (null = not overridden). */
    fun observeExperiment(experimentId: String): Flow<String?> = store.data
        .map { it[experimentKey(experimentId)] }

    suspend fun setDebugOverridesEnabled(enabled: Boolean) =
        store.edit { it[KEY_DEBUG_ENABLED] = enabled }

    suspend fun setFlagOverride(featureKey: String, value: Boolean) =
        store.edit { it[flagKey(featureKey)] = value }

    suspend fun setExperimentOverride(experimentId: String, variant: String) =
        store.edit { it[experimentKey(experimentId)] = variant }

    suspend fun clearFlagOverride(featureKey: String) =
        store.edit { it.remove(flagKey(featureKey)) }

    suspend fun clearAllOverrides() =
        store.edit { it.clear() }
}
