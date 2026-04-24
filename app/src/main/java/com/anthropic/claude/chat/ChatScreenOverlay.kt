package com.anthropic.claude.chat

/**
 * Chat screen overlay states — shown on top of the main chat content.
 */
sealed interface ChatScreenOverlay {
    /**
     * The agent has taken over the browser to perform a computer-use task.
     */
    data class BrowserTakeover(
        val takeoverPath: String? = null,
        val taskId: String? = null,
    ) : ChatScreenOverlay
}
