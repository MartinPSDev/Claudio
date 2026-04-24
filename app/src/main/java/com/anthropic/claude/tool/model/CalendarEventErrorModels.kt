package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Error returned by the event-delete tool.
 */
@Serializable
data class EventDeleteV0OutputEventDeleteError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

/**
 * Error returned by the event-search tool.
 */
@Serializable
data class EventSearchV0OutputEventSearchError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)
