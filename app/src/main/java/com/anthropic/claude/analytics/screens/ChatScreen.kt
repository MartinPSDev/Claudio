package com.anthropic.claude.analytics.screens

/**
 * Analytics screen events for the chat screen.
 */
data class ChatScreen(
    val organizationUuid: String? = null,
    val conversationUuid: String? = null,
) {
    companion object {
        const val SCREEN_NAME = "Chat"
    }
}
