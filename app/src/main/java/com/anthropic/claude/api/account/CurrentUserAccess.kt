package com.anthropic.claude.api.account

import com.anthropic.claude.api.feature.Feature
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUserAccess(
    val features: List<FeatureAccess> = emptyList()
) {
    fun statusFor(feature: Feature): FeatureAccessStatus? {
        return features.firstOrNull { it.feature == feature.value }?.status
    }
}
