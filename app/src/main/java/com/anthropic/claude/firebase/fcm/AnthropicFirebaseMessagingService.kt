package com.anthropic.claude.firebase.fcm

import android.app.PendingIntent
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Intent extra keys for all Claude notification-related intents.
 * Extracted from AnthropicFirebaseMessagingService.smali bytecode.
 */
object FcmIntentExtras {
    const val ACCOUNT_ID          = "com.anthropic.claude.intent.extra.ACCOUNT_ID"
    const val CHAT_ID             = "com.anthropic.claude.intent.extra.CHAT_ID"
    const val SESSION_ID          = "com.anthropic.claude.intent.extra.SESSION_ID"
    const val DISPATCH_SESSION_ID = "com.anthropic.claude.intent.extra.DISPATCH_SESSION_ID"
    const val ORGANIZATION_ID     = "com.anthropic.claude.intent.extra.ORGANIZATION_ID"
    const val NOTIFICATION_ID     = "com.anthropic.claude.intent.extra.NOTIFICATION_ID"
    const val SOURCE              = "com.anthropic.claude.intent.extra.SOURCE"
    const val OPEN_ORBIT          = "com.anthropic.claude.intent.extra.OPEN_ORBIT"
    const val CCR_REQUEST_ID      = "com.anthropic.claude.intent.extra.CCR_REQUEST_ID"
    const val CCR_TOOL_NAME       = "com.anthropic.claude.intent.extra.CCR_TOOL_NAME"
    const val CCR_TOOL_USE_ID     = "com.anthropic.claude.intent.extra.CCR_TOOL_USE_ID"
}

/**
 * Broadcast action strings for Computer-Control Request (CCR) permission responses.
 * Extracted from AnthropicFirebaseMessagingService.smali bytecode.
 */
object CcrActions {
    const val APPROVE           = "com.anthropic.claude.action.CCR_PERMISSION_APPROVE"
    const val DENY              = "com.anthropic.claude.action.CCR_PERMISSION_DENY"
    const val DENY_WITH_COMMENT = "com.anthropic.claude.action.CCR_PERMISSION_DENY_WITH_COMMENT"
}

class AnthropicFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        val category = data["notification_feature_category"] ?: ""
        val title = data["title"] ?: message.notification?.title ?: ""
        val body = data["body"] ?: message.notification?.body ?: ""
        val payload = data["payload"]
        val uri = data["uri"]
        val ccrRequestId = data["ccr_request_id"]
        val ccrToolName = data["ccr_tool_name"]
        val ccrToolUseId = data["ccr_tool_use_id"]
        val ccrCommand = data["ccr_command"]
        val itblField = data["itbl"]

        when (category) {
            CATEGORY_CHAT -> handleChatPush(title, body, uri, payload)
            CATEGORY_CCR -> handleCcrPush(ccrRequestId, ccrToolName, ccrToolUseId, ccrCommand)
            CATEGORY_CONWAY -> handleConwayWakePush(payload)
            CATEGORY_MARKETING -> handleMarketingPush(title, body, uri)
            else -> {}
        }
    }

    override fun onNewToken(token: String) {
        // TODO: persist new FCM token via AccountRepository
    }

    private fun handleChatPush(title: String, body: String, uri: String?, payload: String?) {
    }

    private fun handleCcrPush(
        requestId: String?,
        toolName: String?,
        toolUseId: String?,
        command: String?,
    ) {
    }

    private fun handleConwayWakePush(payload: String?) {
    }

    private fun handleMarketingPush(title: String, body: String, uri: String?) {
    }

    // ─── Routing helpers ──────────────────────────────────────────────────────

    private fun isConwayWake(data: Map<String, String>): Boolean =
        data[KEY_NOTIFICATION_FEATURE_CATEGORY]?.startsWith(CATEGORY_CONWAY) == true

    private fun isCcrRequest(data: Map<String, String>): Boolean =
        data.containsKey(KEY_CCR_REQUEST_ID)

    private fun isLocalMarketing(data: Map<String, String>): Boolean =
        data[KEY_GOOGLE_MESSAGE_ID]?.startsWith(PREFIX_LOCAL_MARKETING) == true

    private fun buildCcrActionIntent(
        action: String,
        requestId: String,
        toolName: String,
        toolUseId: String,
        accountId: String?,
    ): PendingIntent {
        val intent = Intent(action).apply {
            putExtra(FcmIntentExtras.CCR_REQUEST_ID, requestId)
            putExtra(FcmIntentExtras.CCR_TOOL_NAME, toolName)
            putExtra(FcmIntentExtras.CCR_TOOL_USE_ID, toolUseId)
            accountId?.let { putExtra(FcmIntentExtras.ACCOUNT_ID, it) }
        }
        return PendingIntent.getBroadcast(
            this, requestId.hashCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        // Notification feature categories
        private const val CATEGORY_CHAT      = "chat"
        private const val CATEGORY_CCR       = "ccr"
        private const val CATEGORY_CONWAY    = "conway_wake"
        private const val CATEGORY_MARKETING = "marketing"

        // FCM data payload keys (from Smali const-string analysis)
        const val KEY_PAYLOAD                      = "payload"
        const val KEY_BODY                         = "body"
        const val KEY_TITLE                        = "title"
        const val KEY_ITBL                         = "itbl"
        const val KEY_GOOGLE_MESSAGE_ID            = "google.message_id"
        const val KEY_NOTIFICATION_FEATURE_CATEGORY = "notification_feature_category"
        const val KEY_SEEN_MESSAGE_IDS             = "seen_message_ids"
        const val KEY_ACCOUNT_SCOPE                = "AccountScope:"
        const val KEY_ACCOUNT_ID                   = "account_id"
        const val KEY_ORG_ID                       = "org_id"
        const val KEY_CCR_COMMAND                  = "ccr_command"
        const val KEY_CCR_COMMENT                  = "ccr_comment"
        const val KEY_CCR_REQUEST_ID               = "ccr_request_id"
        const val KEY_CCR_TOOL_NAME                = "ccr_tool_name"
        const val KEY_CCR_TOOL_USE_ID              = "ccr_tool_use_id"
        const val PREFIX_CONWAY_WAKE               = "conway_wake_"
        const val PREFIX_LOCAL_MARKETING           = "local-marketing-"
    }
}
