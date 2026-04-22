package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SubscriptionLevel {
    @SerialName("free") FREE,
    @SerialName("pro") PRO,
    @SerialName("team") TEAM,
    @SerialName("enterprise") ENTERPRISE,
    @SerialName("raven") RAVEN,
}
