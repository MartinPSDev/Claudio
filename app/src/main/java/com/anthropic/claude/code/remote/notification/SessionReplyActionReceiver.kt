package com.anthropic.claude.code.remote.notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class SessionReplyActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_SESSION_REPLY) return

        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
        val sessionId = intent.getStringExtra(EXTRA_SESSION_ID)
        val accountId = intent.getStringExtra(EXTRA_ACCOUNT_ID)
        val organizationId = intent.getStringExtra(EXTRA_ORGANIZATION_ID)

        val replyText = RemoteInput.getResultsFromIntent(intent)
            ?.getCharSequence(EXTRA_REPLY_TEXT)
            ?.toString()
            ?.takeIf { it.isNotBlank() }

        if (replyText == null) return

        if (sessionId == null) return
        if (accountId == null) return
        if (organizationId == null) return

        val args = SessionReplyActionWorker.Args(
            sessionId = sessionId,
            accountId = accountId,
            organizationId = organizationId,
            notificationId = notificationId,
            replyText = replyText,
        )

        val request = OneTimeWorkRequestBuilder<SessionReplyActionWorker>()
            .setInputData(args.toWorkData())
            .addTag("account:$accountId")
            .build()

        WorkManager.getInstance(context)
            .beginUniqueWork("session-reply-$sessionId", androidx.work.ExistingWorkPolicy.APPEND_OR_REPLACE, request)
            .enqueue()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.let { nm ->
            val active = nm.activeNotifications.firstOrNull { it.id == notificationId }
            active?.notification?.let { nm.notify(notificationId, it) }
        }
    }

    companion object {
        const val ACTION_SESSION_REPLY = "com.anthropic.claude.action.SESSION_REPLY"
        const val EXTRA_SESSION_ID = "com.anthropic.claude.intent.extra.SESSION_ID"
        const val EXTRA_ACCOUNT_ID = "com.anthropic.claude.intent.extra.ACCOUNT_ID"
        const val EXTRA_ORGANIZATION_ID = "com.anthropic.claude.intent.extra.ORGANIZATION_ID"
        const val EXTRA_NOTIFICATION_ID = "com.anthropic.claude.intent.extra.NOTIFICATION_ID"
        const val EXTRA_REPLY_TEXT = "com.anthropic.claude.intent.extra.REPLY_TEXT"
    }
}
