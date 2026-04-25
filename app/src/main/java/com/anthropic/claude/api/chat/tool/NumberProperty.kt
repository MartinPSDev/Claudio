package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("number")
data class NumberProperty(
    override val description: String? = null,
    val minimum: Double? = null,
    val maximum: Double? = null
) : PropertyDefinition
