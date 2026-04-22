package com.anthropic.claude.models.organization.configtypes

import com.anthropic.claude.api.account.SubscriptionLevel
import com.anthropic.claude.types.strings.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableModelsConfig(
    val models: List<AvailableModel> = emptyList(),
) {
    @Serializable
    data class AvailableModel(
        @SerialName("model_id")
        val modelId: ModelId,
        @SerialName("minimum_tier")
        val minimumTier: SubscriptionLevel?,
    )
}
