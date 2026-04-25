package com.anthropic.claude.tool.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A calendar event entry returned by the calendar integration tool.
 */
@Serializable
data class EventInfo(
    @SerialName("eventId") val eventId: String? = null,
    val title: String? = null,
    @SerialName("startTime") val startTime: String? = null,
    @SerialName("endTime") val endTime: String? = null,
    @SerialName("syncId") val syncId: String? = null,
) {
    override fun toString(): String =
        "EventInfo(eventId=$eventId, title=$title, startTime=$startTime, endTime=$endTime, syncId=$syncId)"
}
