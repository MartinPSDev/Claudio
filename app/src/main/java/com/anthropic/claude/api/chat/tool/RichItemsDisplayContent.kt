package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Rich items display content — a collection of rich UI cards in a tool result.
 */
@Serializable
data class RichItemsDisplayContent(
    val content: List<JsonElement>? = null,
)
