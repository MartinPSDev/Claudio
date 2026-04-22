package com.anthropic.claude.code.remote.notification

import androidx.work.Data
import androidx.work.WorkerParameters
import android.content.Context
import androidx.work.CoroutineWorker

class CCRPermissionActionWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val args = Args.fromWorkData(inputData) ?: return Result.failure()
        return Result.success()
    }

    data class Args(
        val sessionId: String,
        val ccrRequestId: String,
        val toolName: String?,
        val toolUseId: String,
        val accountId: String,
        val organizationId: String,
        val approved: Boolean,
        val comment: String?,
    ) {
        fun toWorkData(): Data = Data.Builder()
            .putString(KEY_SESSION_ID, sessionId)
            .putString(KEY_CCR_REQUEST_ID, ccrRequestId)
            .putString(KEY_TOOL_NAME, toolName)
            .putString(KEY_TOOL_USE_ID, toolUseId)
            .putString(KEY_ACCOUNT_ID, accountId)
            .putString(KEY_ORGANIZATION_ID, organizationId)
            .putBoolean(KEY_APPROVED, approved)
            .putString(KEY_COMMENT, comment)
            .build()

        companion object {
            fun fromWorkData(data: Data): Args? {
                return Args(
                    sessionId = data.getString(KEY_SESSION_ID) ?: return null,
                    ccrRequestId = data.getString(KEY_CCR_REQUEST_ID) ?: return null,
                    toolName = data.getString(KEY_TOOL_NAME),
                    toolUseId = data.getString(KEY_TOOL_USE_ID) ?: "",
                    accountId = data.getString(KEY_ACCOUNT_ID) ?: return null,
                    organizationId = data.getString(KEY_ORGANIZATION_ID) ?: return null,
                    approved = data.getBoolean(KEY_APPROVED, false),
                    comment = data.getString(KEY_COMMENT),
                )
            }
        }
    }

    companion object {
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_CCR_REQUEST_ID = "ccr_request_id"
        private const val KEY_TOOL_NAME = "tool_name"
        private const val KEY_TOOL_USE_ID = "tool_use_id"
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_ORGANIZATION_ID = "organization_id"
        private const val KEY_APPROVED = "approved"
        private const val KEY_COMMENT = "comment"
    }
}
