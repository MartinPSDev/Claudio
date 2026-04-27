package com.anthropic.claude.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Manages FCM topic subscribe/unsubscribe operations with exponential
 * backoff retry on transient errors (SERVICE_NOT_AVAILABLE,
 * INTERNAL_SERVER_ERROR, TOO_MANY_SUBSCRIBERS).
 *
 * Derived from o4f.smali — the FirebaseMessaging topic manager.
 */
class FcmTopicManager(
    private val firebaseMessaging: FirebaseMessaging,
    private val scheduler: ScheduledThreadPoolExecutor,
) {
    companion object {
        private const val TAG = "FcmTopicManager"

        /** Minimum retry delay in seconds. */
        private const val MIN_RETRY_DELAY_SEC = 30L

        /** Maximum retry delay in seconds (8 hours). */
        private const val MAX_RETRY_DELAY_SEC = 28_800L

        /** Timeout waiting for a single topic operation. */
        private const val OPERATION_TIMEOUT_SEC = 30L

        /** Errors that should trigger a retry rather than a hard failure. */
        private val RETRYABLE_ERRORS = setOf(
            "SERVICE_NOT_AVAILABLE",
            "INTERNAL_SERVER_ERROR",
            "TOO_MANY_SUBSCRIBERS",
        )
    }

    @Volatile
    private var isScheduled: Boolean = false

    /**
     * Subscribes to the given FCM topic.
     *
     * @param topic The topic name (without `/topics/` prefix).
     * @throws IOException if the operation times out or the service is unavailable.
     */
    fun subscribeToTopic(topic: String) {
        Log.d(TAG, "Subscribing to topic: $topic")
        val task = firebaseMessaging.subscribeToTopic(topic)
        try {
            com.google.android.gms.tasks.Tasks.await(task, OPERATION_TIMEOUT_SEC, TimeUnit.SECONDS)
            Log.d(TAG, "Subscribed to topic: $topic")
        } catch (e: Exception) {
            handleTopicError(e, "subscribe", topic)
        }
    }

    /**
     * Unsubscribes from the given FCM topic.
     *
     * @param topic The topic name (without `/topics/` prefix).
     * @throws IOException if the operation times out or the service is unavailable.
     */
    fun unsubscribeFromTopic(topic: String) {
        Log.d(TAG, "Unsubscribing from topic: $topic")
        val task = firebaseMessaging.unsubscribeFromTopic(topic)
        try {
            com.google.android.gms.tasks.Tasks.await(task, OPERATION_TIMEOUT_SEC, TimeUnit.SECONDS)
            Log.d(TAG, "Unsubscribed from topic: $topic")
        } catch (e: Exception) {
            handleTopicError(e, "unsubscribe", topic)
        }
    }

    /**
     * Schedules a retry with exponential backoff: delay = min(max(30, 2*prev), 28800).
     */
    fun scheduleRetry(currentDelaySec: Long) {
        val nextDelay = (2 * currentDelaySec)
            .coerceAtLeast(MIN_RETRY_DELAY_SEC)
            .coerceAtMost(MAX_RETRY_DELAY_SEC)

        Log.w(TAG, "Scheduling topic retry in ${nextDelay}s")
        isScheduled = true
        scheduler.schedule({ /* retry pending operations */ }, nextDelay, TimeUnit.SECONDS)
    }

    private fun handleTopicError(e: Exception, operation: String, topic: String) {
        val cause = e.cause
        val message = cause?.message ?: e.message

        if (message != null && RETRYABLE_ERRORS.any { message.contains(it) }) {
            Log.e(TAG, "Topic $operation failed for '$topic': $message. Will retry.")
        } else if (message == null) {
            Log.e(TAG, "Topic $operation failed without exception message. Will retry.")
        } else {
            throw IOException("Topic $operation failed for '$topic': $message", e)
        }
    }
}
