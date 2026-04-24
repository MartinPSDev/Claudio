package com.anthropic.claude.chat.bottomsheet

import kotlinx.serialization.json.JsonElement

/**
 * Additional destinations for the chat screen modal bottom sheet.
 */
sealed interface ChatScreenModalBottomSheetExtras {

    /** Show feedback dialog for a specific message. */
    data class Feedback(
        val messageId: String? = null,
        val messageIndex: Int? = null,
        val feedbackType: String? = null,
        val isFeedbackTypeChangeable: Boolean = false,
    ) : ChatScreenModalBottomSheetExtras

    /** Preview a full-screen image from the conversation. */
    data class PreviewImage(
        val image: JsonElement? = null,
        val showFilename: Boolean = false,
        val showBackButton: Boolean = false,
        val allowDownload: Boolean = false,
    ) : ChatScreenModalBottomSheetExtras

    /** Preview a PDF document from the conversation. */
    data class PreviewPdf(
        val relativeUrl: String? = null,
        val title: String? = null,
        val showBackButton: Boolean = false,
        val allowDownload: Boolean = false,
    ) : ChatScreenModalBottomSheetExtras
}
