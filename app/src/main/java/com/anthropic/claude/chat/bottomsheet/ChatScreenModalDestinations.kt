package com.anthropic.claude.chat.bottomsheet

import com.anthropic.claude.api.chat.messages.Message

/**
 * Consolidated bottom-sheet navigation destinations for the chat screen.
 * Sealed hierarchy covering modal, artifact, and model-selector sheets.
 */
sealed interface ChatScreenModalBottomSheetDestination {
    data object Closed : ChatScreenModalBottomSheetDestination
    data object AgeAssurance : ChatScreenModalBottomSheetDestination
    data object ViewAllChatArtifacts : ChatScreenModalBottomSheetDestination
    data object ViewAllWiggleArtifacts : ChatScreenModalBottomSheetDestination
}

sealed interface ChatScreenArtifactSheetDestination2 {
    data object Closed : ChatScreenArtifactSheetDestination2
}
