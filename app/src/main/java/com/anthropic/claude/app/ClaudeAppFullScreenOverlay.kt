package com.anthropic.claude.app

import com.anthropic.claude.artifact.sheet.ArtifactSheetParams

/** App-level overlay additions for gallery and shared chat full screens. */
sealed interface ClaudeAppFullScreenOverlay {
    /** Full-screen image gallery viewer. */
    data class ImageGalleryFullScreen(
        val initialSelectedId: String? = null,
    ) : ClaudeAppFullScreenOverlay

    /** Full-screen viewer for a shared/public chat snapshot. */
    data class SharedChatFullScreen(
        val snapshotId: String? = null,
    ) : ClaudeAppFullScreenOverlay

    /** Feedback submission sheet. */
    data class FeedbackSheet(
        val initialScreenshotUri: String? = null,
    ) : ClaudeAppFullScreenOverlay
}

/** Settings deep-link destination. */
data class SettingsAppDestination(val params: String? = null)
