package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("unknown")
data class UnknownProperty(
    override val description: String? = null
) : PropertyDefinition
