package com.anthropic.claude.chat.bottomsheet

import com.anthropic.claude.artifact.sheet.ArtifactSheetParams

/**
 * Bottom sheet destinations for artifact-related actions in the chat screen.
 */
sealed interface ChatScreenArtifactSheetDestination {
    /** View an artifact in full detail. */
    data class ViewArtifact(val params: ArtifactSheetParams? = null) : ChatScreenArtifactSheetDestination
}

/**
 * Bottom sheet for sharing a conversation.
 */
data class ShareBottomSheet(
    val chatId: String? = null,
    val messageCount: Int? = null,
)

/**
 * Bottom sheet for the chat message action menu.
 */
data class ChatMenuBottomSheet(
    val messageId: String? = null,
    val messageIndex: Int? = null,
)

/**
 * Bottom sheet for viewing a code block from a message.
 */
data class ViewCodeBottomSheet(
    val code: String? = null,
    val language: String? = null,
)

/**
 * Bottom sheet for previewing a locally cached image.
 */
data class PreviewLocalImageBottomSheet(
    val uriString: String? = null,
    val cacheKey: String? = null,
)
