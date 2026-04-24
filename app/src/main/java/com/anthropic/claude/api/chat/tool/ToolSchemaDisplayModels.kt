package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable

/** A boolean-type JSON Schema property for tool input definitions. */
@Serializable
data class BooleanProperty(
    val description: String? = null,
)

/** Displays a JSON block in the chat UI from a tool result. */
@Serializable
data class JsonBlockDisplayContent(
    @kotlinx.serialization.SerialName("json_block") val jsonBlock: String? = null,
)
