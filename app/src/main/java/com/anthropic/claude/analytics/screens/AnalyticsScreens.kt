package com.anthropic.claude.analytics.screens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Marker interface for all screen analytics events.
 * Implementing classes carry the [screenName] identifier used in analytics pipelines.
 */
interface AnalyticsScreen {
    val screenName: String
}

/**
 * Container object for all concrete analytics screen definitions.
 */
object AnalyticsScreens {

    /**
     * Fired when the user opens the main chat list view.
     * @param organizationUuid Optional UUID of the active organization.
     */
    @Serializable
    data class ChatListScreen(
        @SerialName("organization_uuid") val organizationUuid: String,
    ) : AnalyticsScreen {
        override val screenName: String get() = "chat_list"
    }

    /** Fired when the user opens a specific chat conversation. */
    @Serializable
    data class ChatScreen(
        @SerialName("chat_uuid") val chatUuid: String? = null,
    ) : AnalyticsScreen {
        override val screenName: String get() = "chat"
    }

    /** Fired when the user opens the Projects list. */
    @Serializable
    data class ProjectsScreen(
        @SerialName("organization_uuid") val organizationUuid: String? = null,
    ) : AnalyticsScreen {
        override val screenName: String get() = "projects"
    }

    /** Fired when the user opens a specific project. */
    @Serializable
    data class ProjectScreen(
        @SerialName("project_uuid") val projectUuid: String? = null,
    ) : AnalyticsScreen {
        override val screenName: String get() = "project"
    }

    /** Fired when the user opens the Settings screen. */
    data object SettingsScreen : AnalyticsScreen {
        override val screenName: String get() = "settings"
    }

    /** Fired when the user views the Onboarding flow. */
    data object OnboardingScreen : AnalyticsScreen {
        override val screenName: String get() = "onboarding"
    }
}
