package com.anthropic.claude.tool.model

import kotlinx.serialization.Serializable

/**
 * Input for the CreateFile tool invocation.
 */
@Serializable
data class CreateFileInput(
    val file_text: String? = null,
    val path: String? = null
)

/**
 * Input for the AskUserInput tool invocation.
 */
@Serializable
data class AskUserInputV0Input(
    val question: String? = null,
    val placeholder: String? = null
)

/**
 * Input for the PresentFiles tool invocation.
 */
@Serializable
data class PresentFilesInput(
    val files: List<PresentFileEntry>? = null
)

@Serializable
data class PresentFileEntry(
    val path: String? = null,
    val content: String? = null
)

/**
 * Input for the SuggestConnectors tool invocation.
 */
@Serializable
data class SuggestConnectorsInput(
    val connector_ids: List<String>? = null,
    val reason: String? = null
)

/**
 * Input for the RequestUserBrowserTakeover tool.
 */
@Serializable
data class RequestUserBrowserTakeoverInput(
    val url: String? = null,
    val reason: String? = null
)

/**
 * Input for the PhoneUse tool invocation.
 */
@Serializable
data class PhoneUseInput(
    val action: String? = null,
    val coordinate: List<Int>? = null,
    val text: String? = null,
    val duration: Int? = null,
    val start_coordinate: List<Int>? = null,
    val end_coordinate: List<Int>? = null
)

/**
 * Input for the TimerCreate tool.
 */
@Serializable
data class TimerCreateV0Input(
    val duration_seconds: Int? = null,
    val label: String? = null
)

/**
 * Input for the UserLocation tool.
 */
@Serializable
data class UserLocationV0Input(
    val reason: String? = null
)

/**
 * Input for the ImageSearch tool.
 */
@Serializable
data class ImageSearchInput(
    val query: String? = null,
    val num_results: Int? = null
)

/**
 * Output for the ImageSearch tool.
 */
@Serializable
data class ImageSearchOutput(
    val images: List<ImageSearchResult>? = null
)

@Serializable
data class ImageSearchResult(
    val url: String? = null,
    val title: String? = null,
    val source: String? = null
)

/**
 * Output for EventCreateV1 tool.
 */
@Serializable
data class EventCreateV1Output(
    val result: String? = null
)

/**
 * Output for EventSearchV0 tool.
 */
@Serializable
data class EventSearchV0Output(
    val result: String? = null
)

/**
 * Output for EventUpdateV0 tool.
 */
@Serializable
data class EventUpdateV0Output(
    val result: String? = null
)

/**
 * Output for EventDeleteV0 tool.
 */
@Serializable
data class EventDeleteV0Output(
    val result: String? = null
)

/**
 * Output for UserLocationV0 tool.
 */
@Serializable
data class UserLocationV0Output(
    val result: String? = null
)

/**
 * Output for CalendarSearchV0 tool.
 */
@Serializable
data class CalendarSearchV0Output(
    val result: String? = null
)

/**
 * Output for HealthConnectQueryV0 tool.
 */
@Serializable
data class HealthConnectQueryV0Output(
    val result: String? = null
)

/**
 * Output for UserTimeV0 tool.
 */
@Serializable
data class UserTimeV0Output(
    val timezone: String? = null,
    val local_time: String? = null,
    val utc_offset: String? = null
)

/**
 * Input for HealthConnectDataTypesV0 tool.
 */
@Serializable
data class HealthConnectDataTypesV0Input(
    val category: String? = null
)

/**
 * Output for HealthConnectDataTypesV0 tool.
 */
@Serializable
data class HealthConnectDataTypesV0Output(
    val data_types: List<String>? = null
)

/**
 * Config for health-related tools.
 */
@Serializable
data class HealthToolsConfig(
    val enabled: Boolean = false,
    val available_data_types: List<String>? = null
)
