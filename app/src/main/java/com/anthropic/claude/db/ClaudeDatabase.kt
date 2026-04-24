package com.anthropic.claude.db

/**
 * Stub for the Room database — entity table names extracted from ClaudeDatabase_Impl smali.
 * Full Room implementation would require @Database + @Dao annotations.
 */
object ClaudeDatabase {
    const val TABLE_CACHED_CONVERSATIONS = "cachedConversations"
    const val TABLE_CACHED_MESSAGES      = "cachedMessages"
    const val TABLE_CACHED_PROJECTS      = "cachedProjects"
    const val TABLE_CHAT_ID_LIST_ENTRIES = "chatIdListEntries"
    const val DATABASE_NAME              = "claude.db"
}
