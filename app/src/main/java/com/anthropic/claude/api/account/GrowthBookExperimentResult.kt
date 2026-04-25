package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GrowthBookExperimentResult(
    val inExperiment: Boolean? = null,
    val variationId: Int? = null,
    val value: JsonElement? = null,
    val hashUsed: Boolean? = null
)
