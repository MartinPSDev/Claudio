package com.anthropic.claude.ui.conversation

import com.anthropic.claude.util.ChatDateFormatter

/**
 * Groups conversations by time period for section headers in the list.
 *
 * Sections:
 *   - Starred (always first if any)
 *   - Today
 *   - Yesterday
 *   - Previous 7 Days
 *   - Previous 30 Days
 *   - Older (by month)
 */
object ConversationGrouper {

    data class ConversationSection(
        val title: String,
        val items: List<ConversationListItem>,
    )

    fun groupByTimePeriod(
        conversations: List<ConversationListItem>,
    ): List<ConversationSection> {
        val sections = mutableListOf<ConversationSection>()

        // Starred section
        val starred = conversations.filter { it.isStarred }
        if (starred.isNotEmpty()) {
            sections.add(ConversationSection("Starred", starred))
        }

        // Group non-starred by time period
        val unstarred = conversations.filter { !it.isStarred }
            .sortedByDescending { it.lastMessageTimestamp }

        val now = System.currentTimeMillis()
        val oneDayMs = 24 * 60 * 60 * 1000L
        val sevenDaysMs = 7 * oneDayMs
        val thirtyDaysMs = 30 * oneDayMs

        val today = mutableListOf<ConversationListItem>()
        val yesterday = mutableListOf<ConversationListItem>()
        val previous7Days = mutableListOf<ConversationListItem>()
        val previous30Days = mutableListOf<ConversationListItem>()
        val older = mutableListOf<ConversationListItem>()

        for (item in unstarred) {
            val age = now - item.lastMessageTimestamp
            when {
                age < oneDayMs -> today.add(item)
                age < 2 * oneDayMs -> yesterday.add(item)
                age < sevenDaysMs -> previous7Days.add(item)
                age < thirtyDaysMs -> previous30Days.add(item)
                else -> older.add(item)
            }
        }

        if (today.isNotEmpty()) sections.add(ConversationSection("Today", today))
        if (yesterday.isNotEmpty()) sections.add(ConversationSection("Yesterday", yesterday))
        if (previous7Days.isNotEmpty()) sections.add(ConversationSection("Previous 7 Days", previous7Days))
        if (previous30Days.isNotEmpty()) sections.add(ConversationSection("Previous 30 Days", previous30Days))
        if (older.isNotEmpty()) sections.add(ConversationSection("Older", older))

        return sections
    }
}
