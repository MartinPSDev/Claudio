package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Server-driven configuration for which health tools are enabled.
 */
@Serializable
data class HealthToolsConfig(
    @SerialName("health_connect_data_types_v0") val healthConnectDataTypesV0: Boolean = false,
    @SerialName("health_connect_query_v0") val healthConnectQueryV0: Boolean = false,
)

/**
 * Error returned by the health-connect query tool.
 */
@Serializable
data class HealthConnectQueryV0OutputHealthConnectQueryError(
    @SerialName("error_type") val errorType: String? = null,
    val message: String? = null,
)

/**
 * Input for the map display tool — renders a map with custom markers.
 */
@Serializable
data class MapDisplayV0Input(
    val markers: List<JsonElement>? = null,
    val title: String? = null,
)
