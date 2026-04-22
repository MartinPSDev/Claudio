package com.anthropic.claude.analytics.events

import kotlinx.serialization.Serializable

object PushEvents {

    @Serializable
    enum class SessionReplyOutcome {
        UNSPECIFIED,
        SENT,
        API_FAILURE,
        SCOPE_FAILURE,
        DROPPED_RECEIVER,
    }

    @Serializable
    data class ChatDataPushReceived(
        val organizationId: String,
        val accountId: String,
        val messageId: String,
        val notificationType: String,
    ) : AnalyticsEvent

    @Serializable
    data class ChatNotificationDisplayed(
        val organizationId: String,
        val accountId: String,
        val messageId: String,
        val notificationType: String,
    ) : AnalyticsEvent

    @Serializable
    data class ChatNotificationClicked(
        val organizationId: String,
        val accountId: String,
        val messageId: String,
        val notificationType: String,
    ) : AnalyticsEvent

    @Serializable
    data class ChatNotificationUpsellShown(
        val organizationId: String,
        val accountId: String,
    ) : AnalyticsEvent

    @Serializable
    data class ChatNotificationUpsellClicked(
        val organizationId: String,
        val accountId: String,
    ) : AnalyticsEvent

    @Serializable
    data class CodeSessionNotificationClicked(
        val sessionId: String,
        val accountId: String,
        val organizationId: String,
    ) : AnalyticsEvent

    @Serializable
    data class CodeSessionPermissionActionDisplayed(
        val sessionId: String,
        val ccrRequestId: String,
        val toolName: String?,
        val accountId: String,
        val organizationId: String,
    ) : AnalyticsEvent

    @Serializable
    data class CodeSessionPermissionActionTapped(
        val sessionId: String,
        val ccrRequestId: String,
        val toolName: String?,
        val approved: Boolean,
        val accountId: String,
    ) : AnalyticsEvent

    @Serializable
    data class DispatchNotificationClicked(
        val sessionId: String,
        val accountId: String,
        val organizationId: String,
    ) : AnalyticsEvent

    @Serializable
    data class PushRegistrationSuccess(
        val accountId: String,
        val organizationId: String,
    ) : AnalyticsEvent

    @Serializable
    data class SessionReplyActionSubmitted(
        val sessionId: String,
        val accountId: String,
        val organizationId: String,
    ) : AnalyticsEvent

    @Serializable
    data class SessionReplyActionResult(
        val sessionId: String,
        val accountId: String,
        val organizationId: String,
        val outcome: SessionReplyOutcome,
    ) : AnalyticsEvent
}
