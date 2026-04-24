package com.anthropic.claude.chat.bottomsheet

import kotlinx.serialization.json.JsonElement

/**
 * Bottom sheet destinations for viewing in-chat content blocks and artifacts.
 */
sealed interface ChatScreenContentSheetDestination {
    /** View the content of a specific tool-use block. */
    data class ViewToolContent(val blockId: String? = null) : ChatScreenContentSheetDestination
    /** View the thinking block for a message. */
    data class ViewThinking(val blockId: String? = null) : ChatScreenContentSheetDestination
    /** Voice feedback entry for a conversation. */
    data class VoiceFeedback(val summary: String? = null) : ChatScreenContentSheetDestination
    /** Preview a document file. */
    data class PreviewDocument(val file: JsonElement? = null) : ChatScreenContentSheetDestination
    /** Preview a binary blob. */
    data class PreviewBlob(val file: JsonElement? = null) : ChatScreenContentSheetDestination
    /** Text selection sheet for copying message content. */
    data class SelectText(val messageId: String? = null) : ChatScreenContentSheetDestination
}

/**
 * Artifact-related bottom sheet destinations.
 */
sealed interface ChatScreenArtifactDestination {
    /** View the thinking block inside an artifact panel. */
    data class ViewThinking(val blockId: String? = null) : ChatScreenArtifactDestination
    /** View the analysis output for a tool-use inside an artifact. */
    data class ViewAnalysis(val toolUseId: String? = null) : ChatScreenArtifactDestination
}
