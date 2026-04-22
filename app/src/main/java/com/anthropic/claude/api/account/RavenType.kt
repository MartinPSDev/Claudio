package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RavenType(val value: String) {
    @SerialName("team") TEAM("team"),
    @SerialName("enterprise") ENTERPRISE("enterprise");

    override fun toString(): String = value
}
