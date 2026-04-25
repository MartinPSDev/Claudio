package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("type")
sealed interface PropertyDefinition {
    val description: String?
}
