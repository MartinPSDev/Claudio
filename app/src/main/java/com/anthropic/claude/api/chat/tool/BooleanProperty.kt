package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("boolean")
data class BooleanProperty(
    override val description: String? = null
) : PropertyDefinition
