package com.anthropic.claude.chat.bottomsheet

import kotlinx.serialization.json.JsonElement

/**
 * Extended modal bottom sheet destinations — tool approval and SSH helplines.
 */
sealed interface ChatScreenApprovalSheetDestination {

    /** Remote (cloud) tool approval sheet. */
    data class ToolApproval(
        val toolUseId: String? = null,
        val approvalKey: String? = null,
        val displayContent: JsonElement? = null,
        val iconName: String? = null,
        val integrationName: String? = null,
        val integrationIconUrl: String? = null,
        val hasAllowAlways: Boolean? = null,
    ) : ChatScreenApprovalSheetDestination

    /** Local (on-device) tool approval sheet. */
    data class LocalToolApproval(
        val toolName: String? = null,
        val toolUseId: String? = null,
        val messageId: String? = null,
        val toolInput: JsonElement? = null,
        val toolPreviewInfo: JsonElement? = null,
    ) : ChatScreenApprovalSheetDestination

    /** SSH crisis helplines sheet (safe-messaging compliance). */
    data class SshHelplines(
        val messageId: String? = null,
        val helplineId: String? = null,
        val helplineName: String? = null,
        val phoneNumber: String? = null,
        val smsNumber: String? = null,
        val webChatUrl: String? = null,
    ) : ChatScreenApprovalSheetDestination
}
