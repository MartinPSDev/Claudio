package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Input for the ask-user-input tool (v0). */
@Serializable
data class AskUserInputV0Input(
    val questions: JsonElement? = null,
)

/** Input for the image-search tool. */
@Serializable
data class ImageSearchInput(
    @SerialName("max_results") val maxResults: Int? = null,
    val query: String? = null,
)

/** Input for the suggest-connectors tool. */
@Serializable
data class SuggestConnectorsInput(
    val uuids: List<String>? = null,
)

/** Input for the phone-use tool. */
@Serializable
data class PhoneUseInput(
    val task: String? = null,
    @SerialName("to_number")          val toNumber: String? = null,
    @SerialName("task_description")   val taskDescription: String? = null,
)

/** Input for requesting a browser-takeover. */
@Serializable
data class RequestUserBrowserTakeoverInput(
    @SerialName("progress_summary")   val progressSummary: String? = null,
    @SerialName("user_instructions")  val userInstructions: String? = null,
)

/** Input for presenting files to the user. */
@Serializable
data class PresentFilesInput(
    val filepaths: List<String>? = null,
)

/** Input for creating a file. */
@Serializable
data class CreateFileInput(
    @SerialName("file_text") val fileText: String? = null,
    val path: String? = null,
)

/** Calendar search result (list of calendars). */
@Serializable
data class CalendarSearchV0OutputCalendarSearchResult(
    val calendars: JsonElement? = null,
)
