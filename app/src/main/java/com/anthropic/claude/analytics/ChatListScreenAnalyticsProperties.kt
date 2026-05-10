package com.anthropic.claude.analytics

/**
 * Analytics properties tracked on the chat list screen.
 * Sent to Segment/Datadog when the user interacts with the conversation list.
 */
data class ChatListScreenAnalyticsProperties(
    val conversationCount: Int = 0,
    val starredCount: Int = 0,
    val projectCount: Int = 0,
    val searchQueryLength: Int = 0,
    val isSearchActive: Boolean = false,
    val scrollDepth: Int = 0,
    val selectedSortOrder: String = "recency",
)
