package com.anthropic.claude.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anthropic.claude.db.dao.ConversationDao
import com.anthropic.claude.db.dao.MessageDao
import com.anthropic.claude.db.dao.ProjectDao
import com.anthropic.claude.db.entity.CachedConversationEntity
import com.anthropic.claude.db.entity.CachedMessageEntity
import com.anthropic.claude.db.entity.CachedProjectEntity
import com.anthropic.claude.db.entity.ChatIdListEntryEntity

/**
 * Main Room database for Claude Android.
 * Tables: cachedConversations, cachedMessages, cachedProjects, chatIdListEntries
 */
@Database(
    entities = [
        CachedConversationEntity::class,
        CachedMessageEntity::class,
        CachedProjectEntity::class,
        ChatIdListEntryEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ClaudeRoomDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun projectDao(): ProjectDao

    companion object {
        const val DATABASE_NAME = "claude.db"
    }
}
