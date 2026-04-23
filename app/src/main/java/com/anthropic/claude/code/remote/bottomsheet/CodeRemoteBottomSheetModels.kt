package com.anthropic.claude.code.remote.bottomsheet

/**
 * Navigation destinations for the code remote session bottom sheet.
 * Extracted from CodeRemoteBottomSheetDestination smali files.
 */
sealed interface CodeRemoteBottomSheetDestination {

    /**
     * Show a diff approval view with old/new string content for a file.
     */
    data class ApprovalDiffDetail(
        val filePath: String,
        val oldString: String? = null,
        val newString: String? = null,
    ) : CodeRemoteBottomSheetDestination

    /**
     * Display a channel message payload in the bottom sheet.
     */
    data class ChannelMessage(
        val label: String? = null,
        val payload: String? = null,
    ) : CodeRemoteBottomSheetDestination

    /**
     * Show a code viewer with raw file content.
     */
    data class ViewCode(
        val filePath: String? = null,
        val content: String? = null,
    ) : CodeRemoteBottomSheetDestination
}
