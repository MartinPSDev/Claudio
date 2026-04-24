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

/** DataStore for draft message state per conversation. */
private val Context.draftStore: DataStore<Preferences> by preferencesDataStore(
    name = "chat_drafts",
)

/**
 * Persists per-conversation input drafts across app restarts.
 * Key pattern: "draft_<conversationId>"
 */
class DraftDataStore(private val context: Context) {

    private val store: DataStore<Preferences> get() = context.draftStore

    companion object {
        fun draftKey(conversationId: String) = stringPreferencesKey("draft_$conversationId")
        val KEY_LAST_ACTIVE_CHAT = stringPreferencesKey("last_active_chat_id")
    }

    fun observeDraft(conversationId: String): Flow<String?> = store.data
        .map { it[draftKey(conversationId)] }

    val lastActiveChatId: Flow<String?> = store.data
        .map { it[KEY_LAST_ACTIVE_CHAT] }

    suspend fun saveDraft(conversationId: String, text: String) =
        store.edit { it[draftKey(conversationId)] = text }

    suspend fun clearDraft(conversationId: String) =
        store.edit { it.remove(draftKey(conversationId)) }

    suspend fun setLastActiveChat(chatId: String) =
        store.edit { it[KEY_LAST_ACTIVE_CHAT] = chatId }

    suspend fun clearAll() = store.edit { it.clear() }
}
