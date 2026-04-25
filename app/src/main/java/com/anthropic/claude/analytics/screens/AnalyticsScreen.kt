package com.anthropic.claude.analytics.screens

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Sealed interface for analytics screen tracking.
 * Discriminated by "screenName" field.
 *
 * Known subtypes: ChatListScreen, ChatScreen
 */
@Serializable
@JsonClassDiscriminator("screenName")
sealed interface AnalyticsScreen {
    val screenName: String
}
