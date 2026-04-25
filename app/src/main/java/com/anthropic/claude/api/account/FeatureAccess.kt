package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable

@Serializable
enum class FeatureAccessStatus {
    ENABLED,
    DISABLED,
    UNKNOWN
}

@Serializable
data class FeatureAccess(
    val feature: String,
    val status: FeatureAccessStatus
)
