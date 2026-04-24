package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Result returned by the health-connect query tool.
 */
@Serializable
data class HealthConnectQueryV0OutputHealthConnectQueryResult(
    val message: String? = null,
    @SerialName("query_results") val queryResults: List<JsonElement>? = null,
)
