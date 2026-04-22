package com.anthropic.claude.firebase.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

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

    companion object {
        private const val CATEGORY_CHAT = "chat"
        private const val CATEGORY_CCR = "ccr"
        private const val CATEGORY_CONWAY = "conway_wake"
        private const val CATEGORY_MARKETING = "marketing"
    }
}
