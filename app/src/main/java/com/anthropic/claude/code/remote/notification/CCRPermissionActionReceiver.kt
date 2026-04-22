package com.anthropic.claude.code.remote.notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class CCRPermissionActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return

        val approved = when (action) {
            ACTION_APPROVE -> true
            ACTION_DENY, ACTION_DENY_WITH_COMMENT -> false
            else -> return
        }

        val comment: String? = if (action == ACTION_DENY_WITH_COMMENT) {
            RemoteInput.getResultsFromIntent(intent)
                ?.getCharSequence(EXTRA_CCR_COMMENT)
                ?.toString()
                ?.takeIf { it.isNotBlank() }
        } else null

        val sessionId = intent.getStringExtra(EXTRA_SESSION_ID) ?: return
        val ccrRequestId = intent.getStringExtra(EXTRA_CCR_REQUEST_ID) ?: return
        val toolName = intent.getStringExtra(EXTRA_CCR_TOOL_NAME)
        val toolUseId = intent.getStringExtra(EXTRA_CCR_TOOL_USE_ID) ?: ""
        val accountId = intent.getStringExtra(EXTRA_ACCOUNT_ID) ?: return
        val organizationId = intent.getStringExtra(EXTRA_ORGANIZATION_ID) ?: return
        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.cancel(notificationId)

        val args = CCRPermissionActionWorker.Args(
            sessionId = sessionId,
            ccrRequestId = ccrRequestId,
            toolName = toolName,
            toolUseId = toolUseId,
            accountId = accountId,
            organizationId = organizationId,
            approved = approved,
            comment = comment,
        )

        val request = OneTimeWorkRequestBuilder<CCRPermissionActionWorker>()
            .setInputData(args.toWorkData())
            .addTag("account:$accountId")
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }

    companion object {
        const val ACTION_APPROVE = "com.anthropic.claude.action.CCR_PERMISSION_APPROVE"
        const val ACTION_DENY = "com.anthropic.claude.action.CCR_PERMISSION_DENY"
        const val ACTION_DENY_WITH_COMMENT = "com.anthropic.claude.action.CCR_PERMISSION_DENY_WITH_COMMENT"

        const val EXTRA_SESSION_ID = "com.anthropic.claude.intent.extra.SESSION_ID"
        const val EXTRA_CCR_REQUEST_ID = "com.anthropic.claude.intent.extra.CCR_REQUEST_ID"
        const val EXTRA_CCR_TOOL_NAME = "com.anthropic.claude.intent.extra.CCR_TOOL_NAME"
        const val EXTRA_CCR_TOOL_USE_ID = "com.anthropic.claude.intent.extra.CCR_TOOL_USE_ID"
        const val EXTRA_ACCOUNT_ID = "com.anthropic.claude.intent.extra.ACCOUNT_ID"
        const val EXTRA_ORGANIZATION_ID = "com.anthropic.claude.intent.extra.ORGANIZATION_ID"
        const val EXTRA_NOTIFICATION_ID = "com.anthropic.claude.intent.extra.NOTIFICATION_ID"
        const val EXTRA_CCR_COMMENT = "ccr_comment"
    }
}
