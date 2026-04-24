package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Output of the phone-call-completed tool — call summary with transcript. */
@Serializable
data class PhoneCallCompletedOutput(
    val duration: Int? = null,
    @SerialName("start_time") val startTime: String? = null,
    val transcript: String? = null,
    val error: String? = null,
)

/** Input for the event-search tool (v0). */
@Serializable
data class EventSearchV0Input(
    @SerialName("calendar_id")    val calendarId: String? = null,
    @SerialName("start_time")     val startTime: String? = null,
    @SerialName("end_time")       val endTime: String? = null,
    @SerialName("include_all_day") val includeAllDay: Boolean? = null,
    val limit: Int? = null,
)

/** A single connector item in the suggest-connectors tool output. */
@Serializable
data class SuggestConnectorsOutputConnectorsItem(
    val description: String? = null,
    val name: String? = null,
    val url: String? = null,
    @SerialName("iconUrl")            val iconUrl: String? = null,
    @SerialName("directoryUuid")      val directoryUuid: String? = null,
    @SerialName("installedServerId")   val installedServerId: String? = null,
    @SerialName("installState")        val installState: String? = null,
    @SerialName("isAuthless")          val isAuthless: Boolean? = null,
)

/** Input for the event-create tool (v0). */
@Serializable
data class EventCreateV0Input(
    @SerialName("all_day")     val allDay: Boolean? = null,
    val title: String? = null,
    val description: String? = null,
    val location: String? = null,
    @SerialName("start_time")  val startTime: String? = null,
    @SerialName("end_time")    val endTime: String? = null,
    val recurrence: String? = null,
)
