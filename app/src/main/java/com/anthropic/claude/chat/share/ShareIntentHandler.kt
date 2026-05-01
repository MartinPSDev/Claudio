package com.anthropic.claude.chat.share

import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Handles sharing chat content to external apps via Android's share sheet.
 *
 * Uses `ACTION_SEND` with `text/plain` MIME type and sets both
 * `EXTRA_SUBJECT` and `EXTRA_TEXT`.
 */
object ShareIntentHandler {

    private const val TAG = "ShareIntentHandler"

    /**
     * Opens the system share sheet with the given text content.
     *
     * @param context Activity or application context.
     * @param subject Title for the shared content (e.g. conversation title).
     * @param text The text body to share.
     */
    fun shareText(context: Context, subject: String, text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val chooser = Intent.createChooser(intent, null)
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to compose message", e)
        }
    }
}
