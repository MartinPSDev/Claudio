package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SensitiveTextField(
    val name: String,
    val value: String
)

@Serializable
data class FillSensitiveTextRequest(
    val domain: String? = null,
    val fields: List<SensitiveTextField> = emptyList()
)
