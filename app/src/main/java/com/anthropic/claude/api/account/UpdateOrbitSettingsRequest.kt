package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrbitSettingsRequest(
    val enabled: Boolean? = null,
    val timezone: String? = null
)
