package com.anthropic.claude.models.organization.configtypes

import com.anthropic.claude.types.strings.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelFallback(
    @SerialName("fallback_model_name")
    val fallbackModelName: ModelId,
    @SerialName("display_name")
    val displayName: String,
)

@Serializable
data class ModelFallbacksConfig(
    @SerialName("model_fallbacks")
    val modelFallbacks: Map<String, ModelFallback>? = null,
    @SerialName("cyber_model_fallbacks")
    val cyberModelFallbacks: Map<String, ModelFallback>? = null,
) {
    fun fallbackModel(modelId: ModelId): ModelFallback? =
        modelFallbacks?.get(modelId.value)

    fun cyberFallbackModel(modelId: ModelId): ModelFallback? =
        cyberModelFallbacks?.get(modelId.value)
}
