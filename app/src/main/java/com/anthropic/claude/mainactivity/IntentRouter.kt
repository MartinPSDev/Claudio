package com.anthropic.claude.mainactivity

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.anthropic.claude.model.IncomingPayload

class IntentRouter {
    fun route(intent: Intent?): IncomingPayload? {
        if (intent == null) return null

        val payload = when (intent.action) {
            Intent.ACTION_SEND -> handleSend(intent)
            Intent.ACTION_SEND_MULTIPLE -> handleSendMultiple(intent)
            Intent.ACTION_PROCESS_TEXT -> handleProcessText(intent)
            Intent.ACTION_VIEW -> handleView(intent)
            else -> null
        } ?: handleStartChat(intent)
        return payload?.also { routedPayload ->
            Log.d(TAG, "Intent routed: action=${routedPayload.action}, items=${routedPayload.streamUris.size}")
        }
    }

    private fun handleSend(intent: Intent): IncomingPayload? {
        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        val singleStream = intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        if (text.isNullOrBlank() && singleStream == null) return null
        return IncomingPayload(
            action = Intent.ACTION_SEND,
            text = text,
            streamUris = listOfNotNull(singleStream),
        )
    }

    private fun handleSendMultiple(intent: Intent): IncomingPayload? {
        val streams = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri::class.java).orEmpty()
        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (streams.isEmpty() && text.isNullOrBlank()) return null
        return IncomingPayload(
            action = Intent.ACTION_SEND_MULTIPLE,
            text = text,
            streamUris = streams,
        )
    }

    private fun handleProcessText(intent: Intent): IncomingPayload? {
        val selectedText = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT) ?: return null
        return IncomingPayload(
            action = Intent.ACTION_PROCESS_TEXT,
            text = selectedText,
        )
    }

    private fun handleView(intent: Intent): IncomingPayload? {
        val deepLink = intent.data ?: return null
        return IncomingPayload(
            action = Intent.ACTION_VIEW,
            deepLink = deepLink,
        )
    }

    private fun handleStartChat(intent: Intent): IncomingPayload? {
        val chatId = intent.getStringExtra(EXTRA_START_CHAT_CHAT_ID)
        val startMode = intent.getStringExtra(EXTRA_START_CHAT_MODE)
        if (chatId.isNullOrBlank() && startMode.isNullOrBlank()) return null
        return IncomingPayload(
            action = ACTION_START_CHAT,
            startChatId = chatId,
            startChatMode = startMode,
        )
    }

    companion object {
        private const val TAG = "IntentRouter"
        private const val ACTION_START_CHAT = "com.anthropic.claude.intent.action.START_CHAT"
        private const val EXTRA_START_CHAT_CHAT_ID = "com.anthropic.claude.intent.extra.START_CHAT_CHAT_ID"
        private const val EXTRA_START_CHAT_MODE = "com.anthropic.claude.intent.extra.START_CHAT_MODE"
    }
}
