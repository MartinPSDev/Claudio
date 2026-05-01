package com.anthropic.claude.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Utility for clipboard operations in the chat UI.
 *
 * Used when the user copies code blocks, message text, or artifact content.
 */
object ClipboardHelper {

    /**
     * Copies the given text to the system clipboard.
     *
     * @param context Application or Activity context.
     * @param label A user-visible label for the clipboard content (e.g. "Code block").
     * @param text The text to copy.
     */
    fun copyText(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    /**
     * Returns the current clipboard text, or null if empty.
     */
    fun getClipboardText(context: Context): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip ?: return null
        if (clip.itemCount == 0) return null
        return clip.getItemAt(0).text?.toString()
    }

    /**
     * Returns true if the clipboard has text content available.
     */
    fun hasText(context: Context): Boolean {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return clipboard.hasPrimaryClip() &&
                clipboard.primaryClipDescription?.hasMimeType("text/*") == true
    }
}
