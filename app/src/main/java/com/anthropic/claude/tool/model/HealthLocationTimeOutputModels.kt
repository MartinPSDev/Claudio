package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// ── HealthConnect V0 ──────────────────────────────────────────────────────────

/** Input for the HealthConnect data-types query tool. */
@Serializable
data class HealthConnectDataTypesV0Input(
    @SerialName("search_text") val searchText: String? = null,
)

/** Output of the HealthConnect data-types query tool. */
@Serializable
data class HealthConnectDataTypesV0Output(
    @SerialName("data_types") val dataTypes: JsonElement? = null,
)

/** Input for the HealthConnect query tool (v0). */
@Serializable
data class HealthConnectQueryV0Input(
    val queries: JsonElement? = null,
)

/** Error result from a HealthConnect query. */
@Serializable
data class HealthConnectQueryV0OutputHealthConnectQueryError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

// ── User Location V0 ──────────────────────────────────────────────────────────

/** Input for the user-location tool (v0). */
@Serializable
data class UserLocationV0Input(
    val accuracy: String? = null,
)

/** Error result from a user-location lookup. */
@Serializable
data class UserLocationV0OutputUserLocationError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

// ── User Time V0 ──────────────────────────────────────────────────────────────

/** Output of the user-time tool (v0). */
@Serializable
data class UserTimeV0Output(
    @SerialName("current_time") val currentTime: String? = null,
)

// ── Timer Create V0 ──────────────────────────────────────────────────────────

/** Input for the timer-create tool (v0). */
@Serializable
data class TimerCreateV0Input(
    @SerialName("duration_seconds") val durationSeconds: Int? = null,
    val message: String? = null,
)

// ── Places Map V0 output ──────────────────────────────────────────────────────

/** Output of the places-map-display tool (v0). */
@Serializable
data class PlacesMapDisplayV0Output(
    @SerialName("enriched_places") val enrichedPlaces: JsonElement? = null,
)

// ── Chart Display V0 output ───────────────────────────────────────────────────

/** Output of the chart-display tool (v0). */
@Serializable
data class ChartDisplayV0Output(
    val message: String? = null,
    val status: String? = null,
)
