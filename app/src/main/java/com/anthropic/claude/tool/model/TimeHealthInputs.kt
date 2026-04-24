package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Output of the user-time tool (v0) — returns the current local time. */
@Serializable
data class UserTimeV0Output(
    @SerialName("current_time") val currentTime: String? = null,
)

/** Input for the health-connect data-types tool (v0). */
@Serializable
data class HealthConnectDataTypesV0Input(
    @SerialName("search_text") val searchText: String? = null,
)
