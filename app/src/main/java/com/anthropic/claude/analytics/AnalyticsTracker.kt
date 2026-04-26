package com.anthropic.claude.analytics

import com.anthropic.claude.analytics.events.AnalyticsEvent
import kotlinx.serialization.KSerializer

/**
 * Responsible for identifying the user and dispatching analytics events.
 */
interface AnalyticsTracker {
    
    /** Identifies the user in the analytics provider. */
    fun identify(accountUuid: String?, organizationUuid: String?, email: String?, traits: Any?)

    /** Emits a typed analytics event (like LoginEvents). */
    fun <T : AnalyticsEvent> trackEvent(event: T, serializer: KSerializer<T>)
}
