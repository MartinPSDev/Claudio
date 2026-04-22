package com.anthropic.claude.models.organization

import com.anthropic.claude.types.strings.ModelId
import kotlinx.serialization.Serializable

@Serializable
data class DefaultModelConfig(
    val model: ModelId? = null,
)
