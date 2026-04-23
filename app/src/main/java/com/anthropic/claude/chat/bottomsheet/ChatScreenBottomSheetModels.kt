package com.anthropic.claude.chat.bottomsheet

/**
 * Navigation destinations for the main chat screen modal bottom sheet.
 */
sealed interface ChatScreenModalBottomSheetDestination {

    /**
     * Show the tool approval sheet for a pending computer-control request.
     */
    data class ToolApproval(
        val toolName: String,
        val toolUseId: String? = null,
        val displayContent: String? = null,
        val iconName: String? = null,
        val integrationName: String? = null,
        val integrationIconUrl: String? = null,
        val approvalKey: String? = null,
        val hasAllowAlways: Boolean = false,
    ) : ChatScreenModalBottomSheetDestination
}
