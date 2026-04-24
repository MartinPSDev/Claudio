package com.anthropic.claude.analytics

import kotlinx.serialization.Serializable

/**
 * Analytics properties for the chat screen view event.
 */
@Serializable
data class ChatScreenAnalyticsProperties(
    val organizationUuid: String? = null,
    val conversationUuid: String? = null,
)
