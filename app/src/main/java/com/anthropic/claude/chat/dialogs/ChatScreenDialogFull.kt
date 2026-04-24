package com.anthropic.claude.chat.dialogs

import com.anthropic.claude.artifact.details.ArtifactFullScreenParams

/**
 * Complete set of dialog variants on the chat screen.
 * All declared as data objects / data classes matching smali subclass names.
 */
sealed interface ChatScreenDialogFull {
    data object Dismissed      : ChatScreenDialogFull
    data object Rename         : ChatScreenDialogFull
    data object Delete         : ChatScreenDialogFull
    data object StopResearch   : ChatScreenDialogFull
    data object VoiceShortcut  : ChatScreenDialogFull
    data class  ShareArtifact(val params: ArtifactFullScreenParams? = null) : ChatScreenDialogFull
}
