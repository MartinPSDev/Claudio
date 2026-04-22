package com.anthropic.claude.conway

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ConwayWakeWorker(
    context: Context,
    params: WorkerParameters,
    private val scopeHolder: ConwayScopeHolder,
    private val appForegroundDetector: AppForegroundDetector,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val accountId = inputData.getString(KEY_ACCOUNT_ID) ?: return Result.failure()
        val orgId = inputData.getString(KEY_ORG_ID) ?: return Result.failure()

        if (appForegroundDetector.isInForeground()) {
            return Result.success()
        }

        return try {
            scopeHolder.drainPendingMessages(accountId = accountId, orgId = orgId)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_ACCOUNT_ID = "account_id"
        const val KEY_ORG_ID = "org_id"
    }
}
