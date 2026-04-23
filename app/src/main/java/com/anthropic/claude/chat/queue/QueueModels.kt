package com.anthropic.claude.chat.queue

import com.anthropic.claude.types.strings.ChatId
import androidx.work.Data
import androidx.work.Worker

/**
 * Arguments passed to [QueuedMessageWorker] via WorkManager Data.
 * Serialized as a JSON blob under the "args" key.
 * Fields from QueuedMessageWorker$Args.smali (21KB).
 */
data class QueuedMessageWorkerArgs(
    val chatId: ChatId? = null,
    val accountId: String? = null,
    val organizationId: String? = null,
    val expiresAt: Long? = null,
) {
    companion object {
        /** WorkManager Data key for the serialized args blob. */
        const val KEY_ARGS = "args"
    }

    /** Converts this args object to WorkManager [Data]. */
    fun toWorkData(): Data = Data.Builder()
        .putString(KEY_ARGS, chatId?.value ?: "")
        .build()
}

/**
 * WorkManager worker that dispatches a queued chat message.
 * Handles offline-queued sends: retries when connectivity is restored.
 *
 * Lifecycle (from QueuedMessageWorker.smali, 11KB):
 * 1. Deserialize [QueuedMessageWorkerArgs] from input Data.
 * 2. Validate expiry — cancel if expired.
 * 3. Execute the send request via the network layer.
 * 4. On success: clear the draft; on failure: retry with backoff.
 */
abstract class QueuedMessageWorker {
    abstract fun doWork(): Result

    sealed class Result {
        data object Success : Result()
        data object Retry : Result()
        data class Failure(val reason: String) : Result()
    }
}
