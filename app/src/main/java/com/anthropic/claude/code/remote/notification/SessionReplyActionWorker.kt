package com.anthropic.claude.code.remote.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters

class SessionReplyActionWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val args = Args.fromWorkData(inputData) ?: return Result.failure()
        return Result.success()
    }

    data class Args(
        val sessionId: String,
        val accountId: String,
        val organizationId: String,
        val notificationId: Int,
        val replyText: String,
    ) {
        fun toWorkData(): Data = Data.Builder()
            .putString(KEY_SESSION_ID, sessionId)
            .putString(KEY_ACCOUNT_ID, accountId)
            .putString(KEY_ORGANIZATION_ID, organizationId)
            .putInt(KEY_NOTIFICATION_ID, notificationId)
            .putString(KEY_REPLY_TEXT, replyText)
            .build()

        companion object {
            fun fromWorkData(data: Data): Args? = Args(
                sessionId = data.getString(KEY_SESSION_ID) ?: return null,
                accountId = data.getString(KEY_ACCOUNT_ID) ?: return null,
                organizationId = data.getString(KEY_ORGANIZATION_ID) ?: return null,
                notificationId = data.getInt(KEY_NOTIFICATION_ID, 0),
                replyText = data.getString(KEY_REPLY_TEXT) ?: return null,
            )
        }
    }

    companion object {
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_ORGANIZATION_ID = "organization_id"
        private const val KEY_NOTIFICATION_ID = "notification_id"
        private const val KEY_REPLY_TEXT = "reply_text"
    }
}
