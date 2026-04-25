package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("integer")
data class IntegerProperty(
    override val description: String? = null,
    val minimum: Int? = null,
    val maximum: Int? = null
) : PropertyDefinition
