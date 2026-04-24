package com.anthropic.claude.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Read consistency level for API operations. */
@Serializable
enum class ConsistencyLevel {
    @SerialName("strong")   STRONG,
    @SerialName("EVENTUAL") EVENTUAL,
}
