package com.anthropic.claude.chat

/**
 * Extended ChatScreenOverlay states.
 */
sealed interface ChatScreenOverlayFull {
    /** The agent has taken over the browser (computer-use). */
    data class BrowserTakeover(
        val takeoverPath: String? = null,
        val taskId: String? = null,
    ) : ChatScreenOverlayFull

    /** No overlay shown. */
    data object None : ChatScreenOverlayFull
}
