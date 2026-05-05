package com.anthropic.claude.chat.export

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.anthropic.claude.chat.viewmodel.ChatMessage
import com.anthropic.claude.chat.viewmodel.MessageRole
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Exports chat conversations to various formats.
 *
 * Supported formats:
 *   - Markdown (.md) — default, preserves code blocks and formatting
 *   - Plain text (.txt) — stripped formatting
 *
 * Files are saved to the device's Downloads folder using MediaStore
 * for API 29+ compatibility.
 */
object ConversationExporter {

    private const val TAG = "ConversationExporter"

    /**
     * Export format options.
     */
    enum class Format(val extension: String, val mimeType: String) {
        MARKDOWN("md", "text/markdown"),
        TEXT("txt", "text/plain"),
    }

    /**
     * Exports a conversation to the Downloads folder.
     *
     * @param context Application context.
     * @param conversationTitle Title of the conversation.
     * @param messages List of messages to export.
     * @param format Export format.
     * @return true if export was successful.
     */
    fun export(
        context: Context,
        conversationTitle: String,
        messages: List<ChatMessage>,
        format: Format = Format.MARKDOWN,
    ): Boolean {
        val fileName = buildFileName(conversationTitle, format)
        val content = formatContent(conversationTitle, messages, format)

        return try {
            writeToDownloads(context, fileName, format.mimeType, content)
            Log.i(TAG, "Exported conversation to $fileName")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to export conversation", e)
            false
        }
    }

    private fun formatContent(
        title: String,
        messages: List<ChatMessage>,
        format: Format,
    ): String {
        val sb = StringBuilder()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        when (format) {
            Format.MARKDOWN -> {
                sb.appendLine("# $title")
                sb.appendLine()
                for (msg in messages) {
                    val role = when (msg.role) {
                        MessageRole.USER -> "**You**"
                        MessageRole.ASSISTANT -> "**Claude**"
                        MessageRole.SYSTEM -> "**System**"
                    }
                    val time = dateFormatter.format(Date(msg.timestamp))
                    sb.appendLine("### $role — $time")
                    sb.appendLine()
                    sb.appendLine(msg.content)
                    sb.appendLine()
                    sb.appendLine("---")
                    sb.appendLine()
                }
            }
            Format.TEXT -> {
                sb.appendLine(title)
                sb.appendLine("=".repeat(title.length))
                sb.appendLine()
                for (msg in messages) {
                    val role = when (msg.role) {
                        MessageRole.USER -> "You"
                        MessageRole.ASSISTANT -> "Claude"
                        MessageRole.SYSTEM -> "System"
                    }
                    val time = dateFormatter.format(Date(msg.timestamp))
                    sb.appendLine("[$role] $time")
                    sb.appendLine(msg.content)
                    sb.appendLine()
                }
            }
        }

        return sb.toString()
    }

    private fun buildFileName(title: String, format: Format): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val safeTitle = title.take(50)
            .replace(Regex("[^a-zA-Z0-9 ]"), "")
            .replace(" ", "_")
            .ifEmpty { "conversation" }
        return "${safeTitle}_$timestamp.${format.extension}"
    }

    private fun writeToDownloads(
        context: Context,
        fileName: String,
        mimeType: String,
        content: String,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, mimeType)
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI, values,
            ) ?: throw IllegalStateException("Failed to create MediaStore entry")

            context.contentResolver.openOutputStream(uri)?.use { stream ->
                stream.write(content.toByteArray())
            }
        } else {
            @Suppress("DEPRECATION")
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = java.io.File(dir, fileName)
            file.writeText(content)
        }
    }
}
