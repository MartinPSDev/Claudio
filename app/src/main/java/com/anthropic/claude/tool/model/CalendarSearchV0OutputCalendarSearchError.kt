package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Error returned by the calendar-search tool (v0).
 */
@Serializable
data class CalendarSearchV0OutputCalendarSearchError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)
