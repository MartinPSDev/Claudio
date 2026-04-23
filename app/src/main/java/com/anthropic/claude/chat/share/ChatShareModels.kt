package com.anthropic.claude.chat.share

/**
 * Navigation destination sealed class for the shared chat modal bottom sheet.
 * Extracted from SharedChatModalBottomSheetDestination smali files.
 */
sealed interface SharedChatModalBottomSheetDestination {

    /**
     * Show the list of citation sources for a given message.
     */
    data class ViewSources(
        val title: String? = null,
        val sources: List<String> = emptyList(),
    ) : SharedChatModalBottomSheetDestination

    /**
     * Show a combined/merged sources view.
     */
    data class ViewCombinedSources(
        val title: String? = null,
        val sources: List<String> = emptyList(),
    ) : SharedChatModalBottomSheetDestination

    /**
     * Preview a web link before opening it in the browser.
     */
    data class PreviewLink(
        val source: String? = null,
        val url: String? = null,
    ) : SharedChatModalBottomSheetDestination

    /**
     * Show collapsed tool calls that were hidden in the main chat view.
     */
    data class ViewCollapsedTools(
        val toolCount: Int = 0,
    ) : SharedChatModalBottomSheetDestination
}
