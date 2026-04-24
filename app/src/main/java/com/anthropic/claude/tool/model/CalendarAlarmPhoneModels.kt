package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Input for the calendar event search tool.
 */
@Serializable
data class EventSearchV0Input(
    @SerialName("calendar_id") val calendarId: String? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time") val endTime: String? = null,
    @SerialName("include_all_day") val includeAllDay: Boolean = false,
    val limit: Int? = null,
)

/**
 * Input for the alarm creation tool.
 */
@Serializable
data class AlarmCreateV0Input(
    val days: List<String>? = null,
    val hour: Int? = null,
    val minute: Int? = null,
    val message: String? = null,
    val vibrate: Boolean = true,
)

/**
 * Output returned after a phone call tool completes.
 */
@Serializable
data class PhoneCallCompletedOutput(
    val duration: Int? = null,
    @SerialName("start_time") val startTime: String? = null,
    val transcript: String? = null,
    val error: String? = null,
)
