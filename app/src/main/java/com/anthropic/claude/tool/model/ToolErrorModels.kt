package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Error returned by the calendar search tool.
 */
@Serializable
data class CalendarSearchV0OutputCalendarSearchError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

/**
 * Error returned by the user location tool.
 */
@Serializable
data class UserLocationV0OutputUserLocationError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

/**
 * Error returned by the v1 event-create tool.
 */
@Serializable
data class EventCreateV1OutputEventCreateV1Error(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

/**
 * Error returned by the v0 event-update tool.
 */
@Serializable
data class EventUpdateV0OutputEventUpdateError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)
