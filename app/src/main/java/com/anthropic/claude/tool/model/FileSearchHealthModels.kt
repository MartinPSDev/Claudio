package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Result of an event-update tool call (v0).
 */
@Serializable
data class EventUpdateV0OutputEventUpdateResult(
    @SerialName("update_results") val updateResults: List<JsonElement>? = null,
)

/**
 * Input for the image-search tool.
 */
@Serializable
data class ImageSearchInput(
    val query: String? = null,
    @SerialName("max_results") val maxResults: Int? = null,
)

/**
 * Input for the create-file tool used in remote code sessions.
 */
@Serializable
data class CreateFileInput(
    @SerialName("file_text") val fileText: String? = null,
    val path: String? = null,
)

/**
 * Output of the health-connect-data-types tool (v0).
 */
@Serializable
data class HealthConnectDataTypesV0Output(
    @SerialName("data_types") val dataTypes: List<JsonElement>? = null,
)

/**
 * Input for the health-connect-query tool (v0).
 */
@Serializable
data class HealthConnectQueryV0Input(
    val queries: List<JsonElement>? = null,
)
