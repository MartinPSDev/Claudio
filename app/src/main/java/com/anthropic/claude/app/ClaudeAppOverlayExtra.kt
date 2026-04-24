package com.anthropic.claude.app

/**
 * Additional app-level overlays extracted from ClaudeAppOverlay$* smali subclasses.
 */
sealed interface ClaudeAppOverlayExtra {
    /** No overlay shown. */
    data object None                : ClaudeAppOverlayExtra
    /** Create a new cloud execution environment. */
    data object CreateEnvironment   : ClaudeAppOverlayExtra
    /** Create a new project. */
    data object CreateProject       : ClaudeAppOverlayExtra
}
