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

/** DataStore for active session metadata — separate file from user prefs. */
private val Context.sessionStore: DataStore<Preferences> by preferencesDataStore(
    name = "session",
)

/**
 * Stores lightweight session metadata that survives process death
 * (account UUID, org UUID, bootstrap timestamp, auth cookie presence flag).
 */
class SessionDataStore(private val context: Context) {

    private val store: DataStore<Preferences> get() = context.sessionStore

    companion object {
        val KEY_ACCOUNT_UUID    = stringPreferencesKey("account_uuid")
        val KEY_ORG_UUID        = stringPreferencesKey("organization_uuid")
        val KEY_DISPLAY_EMAIL   = stringPreferencesKey("display_email")
        val KEY_HAS_COOKIE      = booleanPreferencesKey("has_session_cookie")
        val KEY_BOOTSTRAP_TS    = stringPreferencesKey("bootstrap_timestamp")
    }

    val accountUuid: Flow<String?>    = store.data.map { it[KEY_ACCOUNT_UUID] }
    val organizationUuid: Flow<String?> = store.data.map { it[KEY_ORG_UUID] }
    val displayEmail: Flow<String?>   = store.data.map { it[KEY_DISPLAY_EMAIL] }
    val hasSessionCookie: Flow<Boolean> = store.data.map { it[KEY_HAS_COOKIE] ?: false }
    val bootstrapTimestamp: Flow<String?> = store.data.map { it[KEY_BOOTSTRAP_TS] }

    suspend fun saveSession(
        accountUuid: String,
        orgUuid: String,
        displayEmail: String,
        bootstrapTs: String,
    ) = store.edit {
        it[KEY_ACCOUNT_UUID]  = accountUuid
        it[KEY_ORG_UUID]      = orgUuid
        it[KEY_DISPLAY_EMAIL] = displayEmail
        it[KEY_HAS_COOKIE]    = true
        it[KEY_BOOTSTRAP_TS]  = bootstrapTs
    }

    suspend fun setHasCookie(hasCookie: Boolean) =
        store.edit { it[KEY_HAS_COOKIE] = hasCookie }

    suspend fun clearSession() = store.edit { it.clear() }
}
