package com.anthropic.claude.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Tracks the latest completed/seen message per conversation.
 * Stored in a dedicated SharedPreferences file: [PREFS_NAME].
 */
class LatestSeenMessagesStore(
    private val prefs: SharedPreferences,
) {
    companion object {
        const val PREFS_NAME = "app_prefs_latest_seen_completed_messages"

        fun from(context: Context): LatestSeenMessagesStore =
            LatestSeenMessagesStore(
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE),
            )
    }

    /**
     * Returns the UUID of the last seen completed message for the given conversation.
     * Returns null if no message has been seen yet.
     */
    fun getLatestSeenMessageId(conversationUuid: String): String? =
        prefs.getString(conversationUuid, null)

    /**
     * Persists the UUID of the last seen completed message for the given conversation.
     */
    fun setLatestSeenMessageId(conversationUuid: String, messageUuid: String) =
        prefs.edit { putString(conversationUuid, messageUuid) }

    /**
     * Clears the tracking record for a single conversation (e.g. on delete).
     */
    fun clearConversation(conversationUuid: String) =
        prefs.edit { remove(conversationUuid) }

    /**
     * Returns all tracked conversation UUIDs.
     */
    fun getAllTrackedConversations(): Set<String> =
        prefs.all.keys.toSet()
}
