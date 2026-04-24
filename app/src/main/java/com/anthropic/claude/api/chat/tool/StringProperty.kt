package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable

/** A string-type JSON Schema property for tool input definitions. */
@Serializable
data class StringProperty(
    val description: String? = null,
    val enum: List<String>? = null,
    val minLength: Int? = null,
    val maxLength: Int? = null,
    val pattern: String? = null,
)
