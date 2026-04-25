package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("object")
data class ObjectProperty(
    override val description: String? = null,
    val properties: Map<String, PropertyDefinition>? = null,
    val required: List<String>? = null
) : PropertyDefinition
