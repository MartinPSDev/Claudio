package com.anthropic.claude.analytics.events

import kotlinx.serialization.Serializable

/**
 * Analytics events for the Login Flow.
 */
object LoginEvents {
    @Serializable
    class EmailLoginSendingMagicLink : AnalyticsEvent

    @Serializable
    class EmailLoginMagicLinkSent : AnalyticsEvent

    @Serializable
    class EmailLoginMagicLinkSendError : AnalyticsEvent

    @Serializable
    class SignInWithGoogleError : AnalyticsEvent

    @Serializable
    data class ManagedLoginBlocked(val intent: String) : AnalyticsEvent
}
