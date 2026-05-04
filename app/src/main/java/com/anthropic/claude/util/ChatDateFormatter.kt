package com.anthropic.claude.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Formats timestamps into human-readable relative or absolute strings
 * matching the production app's display conventions.
 *
 * Display rules:
 *   - < 1 min ago → "Just now"
 *   - < 60 min ago → "Xm ago"
 *   - < 24 hours ago → "Xh ago"
 *   - Today → time only (e.g., "2:30 PM" or "14:30")
 *   - Yesterday → "Yesterday"
 *   - This week → day name (e.g., "Monday")
 *   - Older → date (e.g., "Jan 15, 2026")
 */
object ChatDateFormatter {

    private val timeFormat12h = SimpleDateFormat("h:mm a", Locale.getDefault())
    private val timeFormat24h = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    /**
     * Formats a timestamp for display in the conversation list.
     *
     * @param timestampMs Unix timestamp in milliseconds.
     * @param use24Hour Whether to use 24-hour time format.
     * @return Human-readable time string.
     */
    fun formatConversationTimestamp(timestampMs: Long, use24Hour: Boolean = false): String {
        val now = System.currentTimeMillis()
        val diffMs = now - timestampMs
        val diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMs)
        val diffHours = TimeUnit.MILLISECONDS.toHours(diffMs)

        return when {
            diffMinutes < 1 -> "Just now"
            diffMinutes < 60 -> "${diffMinutes}m ago"
            diffHours < 24 -> "${diffHours}h ago"
            isToday(timestampMs) -> {
                val fmt = if (use24Hour) timeFormat24h else timeFormat12h
                fmt.format(Date(timestampMs))
            }
            isYesterday(timestampMs) -> "Yesterday"
            isThisWeek(timestampMs) -> dayFormat.format(Date(timestampMs))
            else -> dateFormat.format(Date(timestampMs))
        }
    }

    /**
     * Formats a timestamp for display as a message group separator.
     *
     * @param timestampMs Unix timestamp in milliseconds.
     * @param use24Hour Whether to use 24-hour time format.
     * @return Time string (e.g., "2:30 PM" or "14:30").
     */
    fun formatMessageTime(timestampMs: Long, use24Hour: Boolean = false): String {
        val fmt = if (use24Hour) timeFormat24h else timeFormat12h
        return fmt.format(Date(timestampMs))
    }

    private fun isToday(timestampMs: Long): Boolean {
        val cal = Calendar.getInstance()
        val todayDay = cal.get(Calendar.DAY_OF_YEAR)
        val todayYear = cal.get(Calendar.YEAR)
        cal.timeInMillis = timestampMs
        return cal.get(Calendar.DAY_OF_YEAR) == todayDay && cal.get(Calendar.YEAR) == todayYear
    }

    private fun isYesterday(timestampMs: Long): Boolean {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayDay = cal.get(Calendar.DAY_OF_YEAR)
        val yesterdayYear = cal.get(Calendar.YEAR)
        cal.timeInMillis = timestampMs
        return cal.get(Calendar.DAY_OF_YEAR) == yesterdayDay && cal.get(Calendar.YEAR) == yesterdayYear
    }

    private fun isThisWeek(timestampMs: Long): Boolean {
        val now = System.currentTimeMillis()
        val diffDays = TimeUnit.MILLISECONDS.toDays(now - timestampMs)
        return diffDays < 7
    }
}
