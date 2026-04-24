package com.anthropic.claude.app

import com.anthropic.claude.artifact.details.ArtifactFullScreenParams

/**
 * Global overlay states shown on top of the entire app (not just the chat screen).
 */
sealed interface ClaudeAppOverlay {

    /** A bottom sheet from Grove (policy/compliance notice). */
    data class GroveNoticeBottomSheet(
        val location: String? = null,
    ) : ClaudeAppOverlay

    /** Full-screen artifact viewer. */
    data class ArtifactFullScreen(
        val params: ArtifactFullScreenParams? = null,
    ) : ClaudeAppOverlay
}
