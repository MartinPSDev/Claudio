package com.anthropic.claude.settings

import com.anthropic.claude.api.account.RavenType
import com.anthropic.claude.api.account.SubscriptionLevel
import com.anthropic.claude.api.mcp.DirectoryServer
import com.anthropic.claude.types.strings.McpServerId
import kotlinx.serialization.Serializable

sealed interface SettingsAppScreen {

    @Serializable
    data object SettingsScreen : SettingsAppScreen

    @Serializable
    data object ProfileScreen : SettingsAppScreen

    @Serializable
    data class BillingScreen(
        val subscriptionLevel: SubscriptionLevel,
        val ravenType: RavenType? = null,
    ) : SettingsAppScreen

    @Serializable
    data object CapabilitiesScreen : SettingsAppScreen

    @Serializable
    data object ConnectorsScreen : SettingsAppScreen

    @Serializable
    data object ConnectorDirectory : SettingsAppScreen

    @Serializable
    data class ConnectorDirectoryDetail(
        val server: DirectoryServer,
    ) : SettingsAppScreen

    @Serializable
    data object ConnectedAppsScreen : SettingsAppScreen

    @Serializable
    data object PermissionsScreen : SettingsAppScreen

    @Serializable
    data object CalendarPermissionScreen : SettingsAppScreen

    @Serializable
    data object LocationPermissionScreen : SettingsAppScreen

    @Serializable
    data object HealthPermissionScreen : SettingsAppScreen

    @Serializable
    data object DebugSettingsScreen : SettingsAppScreen

    @Serializable
    data object MobileAppFeedbackScreen : SettingsAppScreen

    @Serializable
    data object LicensesScreen : SettingsAppScreen

    @Serializable
    data object NotificationSettingsScreen : SettingsAppScreen

    @Serializable
    data object PrivacyScreen : SettingsAppScreen

    @Serializable
    data object SharedLinksScreen : SettingsAppScreen

    @Serializable
    data class McpServerToolsScreen(
        val serverId: McpServerId,
    ) : SettingsAppScreen

    @Serializable
    data object UsageScreen : SettingsAppScreen
}
