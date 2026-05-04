package com.anthropic.claude.di

import android.content.Context
import androidx.room.Room
import com.anthropic.claude.app.notifications.NotificationChannels
import com.anthropic.claude.app.trusteddevice.TrustedDeviceEnrollmentRepository
import com.anthropic.claude.app.trusteddevice.TrustedDeviceEnrollmentViewModel
import com.anthropic.claude.app.trusteddevice.TrustedDeviceStore
import com.anthropic.claude.core.telemetry.SentryBeforeSendFilter
import com.anthropic.claude.datastore.DraftDataStore
import com.anthropic.claude.datastore.GrowthBookDataStore
import com.anthropic.claude.datastore.SessionDataStore
import com.anthropic.claude.datastore.UserPreferencesDataStore
import com.anthropic.claude.db.ClaudeRoomDatabase
import com.anthropic.claude.db.dao.ConversationDao
import com.anthropic.claude.db.dao.MessageDao
import com.anthropic.claude.db.dao.ProjectDao
import com.anthropic.claude.login.ManagedLoginProvider
import com.anthropic.claude.login.repository.LoginRepositoryImpl
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.NetworkingModule
import com.anthropic.claude.repository.AccountRepository
import com.anthropic.claude.repository.ConversationRepository
import com.anthropic.claude.repository.ExperienceRepository
import com.anthropic.claude.repository.McpRepository
import com.anthropic.claude.repository.MessageRepository
import com.anthropic.claude.repository.ProjectRepository
import com.anthropic.claude.repository.SessionRepository
import com.anthropic.claude.settings.InternalPreferencesStore
import com.anthropic.claude.settings.LatestSeenMessagesStore

/**
 * Manual DI container — provides singletons for the entire application.
 * Replace with Hilt/Koin if DI framework is introduced.
 */
class AppContainer(context: Context) {

    private val appContext: Context = context.applicationContext

    // ── Database ─────────────────────────────────────────────────────────────

    val database: ClaudeRoomDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            ClaudeRoomDatabase::class.java,
            ClaudeRoomDatabase.DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()
    }

    val conversationDao: ConversationDao get() = database.conversationDao()
    val messageDao: MessageDao           get() = database.messageDao()
    val projectDao: ProjectDao           get() = database.projectDao()

    // ── Preferences / Settings ────────────────────────────────────────────────

    val internalPreferencesStore: InternalPreferencesStore by lazy {
        InternalPreferencesStore.from(appContext)
    }

    val latestSeenMessagesStore: LatestSeenMessagesStore by lazy {
        LatestSeenMessagesStore.from(appContext)
    }

    // ── Networking ────────────────────────────────────────────────────────────

    val apiClient: AnthropicApiClient by lazy {
        NetworkingModule.provideApiClient(
            context = appContext,
            onAuthExpired = { sessionRepository.onAuthExpired() },
        )
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    val managedLoginProvider: ManagedLoginProvider by lazy {
        ManagedLoginProvider { true }
    }

    val loginRepository: LoginRepositoryImpl by lazy {
        LoginRepositoryImpl(apiClient)
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

    // ── Trusted Device ────────────────────────────────────────────────────────

    val trustedDeviceStore: TrustedDeviceStore by lazy {
        TrustedDeviceStore.from(appContext)
    }

    val trustedDeviceEnrollmentRepository: TrustedDeviceEnrollmentRepository by lazy {
        TrustedDeviceEnrollmentRepository(apiClient)
    }

    // ── Telemetry ─────────────────────────────────────────────────────────────

    val sentryBeforeSendFilter: SentryBeforeSendFilter by lazy {
        SentryBeforeSendFilter(internalPreferencesStore)
    }

    // ── DataStore ─────────────────────────────────────────────────────────────

    val userPreferencesDataStore: UserPreferencesDataStore by lazy {
        UserPreferencesDataStore(appContext)
    }

    val sessionDataStore: SessionDataStore by lazy {
        SessionDataStore(appContext)
    }

    val growthBookDataStore: GrowthBookDataStore by lazy {
        GrowthBookDataStore(appContext)
    }

    val draftDataStore: DraftDataStore by lazy {
        DraftDataStore(appContext)
    }

    // ── Feature Flags ─────────────────────────────────────────────────────────

    val featureFlagProvider: com.anthropic.claude.configs.GrowthBookFeatureFlagProvider by lazy {
        com.anthropic.claude.configs.GrowthBookFeatureFlagProvider(growthBookDataStore)
    }

    // ── Network ───────────────────────────────────────────────────────────────

    val networkConnectivityMonitor: com.anthropic.claude.networking.NetworkConnectivityMonitor by lazy {
        com.anthropic.claude.networking.NetworkConnectivityMonitor(appContext)
    }
}

