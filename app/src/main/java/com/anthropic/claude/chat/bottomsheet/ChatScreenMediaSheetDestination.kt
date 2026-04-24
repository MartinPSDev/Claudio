package com.anthropic.claude.chat.bottomsheet

import kotlinx.serialization.json.JsonElement

/**
 * Rich-media preview and feedback bottom sheet destinations.
 */
sealed interface ChatScreenMediaSheetDestination {

    /** Feedback sheet for a specific message. */
    data class Feedback(
        val messageId: String? = null,
        val messageIndex: Int? = null,
        val feedbackType: String? = null,
        val isFeedbackTypeChangeable: Boolean? = null,
    ) : ChatScreenMediaSheetDestination

    /** Full-screen image preview. */
    data class PreviewImage(
        val image: JsonElement? = null,
        val allowDownload: Boolean? = null,
        val showBackButton: Boolean? = null,
        val showFilename: Boolean? = null,
    ) : ChatScreenMediaSheetDestination

    /** Full-screen PDF preview. */
    data class PreviewPdf(
        val relativeUrl: String? = null,
        val title: String? = null,
        val allowDownload: Boolean? = null,
        val showBackButton: Boolean? = null,
    ) : ChatScreenMediaSheetDestination

    /** Combined citations + other sources viewer. */
    data class ViewCombinedSources(
        val citations: JsonElement? = null,
        val otherSources: JsonElement? = null,
    ) : ChatScreenMediaSheetDestination
}
