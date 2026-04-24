package com.anthropic.claude.di

import android.content.Context
import com.anthropic.claude.datastore.DraftDataStore
import com.anthropic.claude.datastore.GrowthBookDataStore
import com.anthropic.claude.datastore.SessionDataStore
import com.anthropic.claude.datastore.UserPreferencesDataStore
import com.anthropic.claude.db.ClaudeRoomDatabase
import com.anthropic.claude.db.dao.ConversationDao
import com.anthropic.claude.db.dao.MessageDao
import com.anthropic.claude.db.dao.ProjectDao
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.NetworkingModule
import com.anthropic.claude.repository.AccountRepository
import com.anthropic.claude.repository.ConversationRepository
import com.anthropic.claude.repository.ExperienceRepository
import com.anthropic.claude.repository.McpRepository
import com.anthropic.claude.repository.MessageRepository
import com.anthropic.claude.repository.ProjectRepository
import com.anthropic.claude.repository.SessionRepository
import androidx.room.Room

/**
 * Manual DI container — provides singletons for the entire application.
 * Replace with Hilt/Koin if DI framework is introduced.
 */
class AppContainer(context: Context) {

    // ── Database ─────────────────────────────────────────────────────────────

    val database: ClaudeRoomDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            ClaudeRoomDatabase::class.java,
            ClaudeRoomDatabase.DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()
    }

    val conversationDao: ConversationDao get() = database.conversationDao()
    val messageDao: MessageDao           get() = database.messageDao()
    val projectDao: ProjectDao           get() = database.projectDao()

    // ── Networking ────────────────────────────────────────────────────────────

    val apiClient: AnthropicApiClient by lazy {
        NetworkingModule.provideApiClient(
            context = context,
            onAuthExpired = { sessionRepository.onAuthExpired() },
        )
    }

    // ── Repositories ──────────────────────────────────────────────────────────

    val sessionRepository: SessionRepository by lazy {
        SessionRepository(apiClient)
    }

    val accountRepository: AccountRepository by lazy {
        AccountRepository(apiClient)
    }

    val conversationRepository: ConversationRepository by lazy {
        ConversationRepository(apiClient, conversationDao)
    }

    val messageRepository: MessageRepository by lazy {
        MessageRepository(apiClient, messageDao)
    }

    val projectRepository: ProjectRepository by lazy {
        ProjectRepository(apiClient, projectDao)
    }

    val mcpRepository: McpRepository by lazy {
        McpRepository(apiClient)
    }

    val experienceRepository: ExperienceRepository by lazy {
        ExperienceRepository(apiClient)
    }

    // ── DataStore ─────────────────────────────────────────────────────────────

    val userPreferencesDataStore: UserPreferencesDataStore by lazy {
        UserPreferencesDataStore(context.applicationContext)
    }

    val sessionDataStore: SessionDataStore by lazy {
        SessionDataStore(context.applicationContext)
    }

    val growthBookDataStore: GrowthBookDataStore by lazy {
        GrowthBookDataStore(context.applicationContext)
    }

    val draftDataStore: DraftDataStore by lazy {
        DraftDataStore(context.applicationContext)
    }
}
