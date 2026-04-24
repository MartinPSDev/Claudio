package com.anthropic.claude.chat.bottomsheet

import kotlinx.serialization.json.JsonElement

/**
 * Bottom sheet showing source citations for a specific message.
 */
data class ViewSources(
    val title: String? = null,
    val sources: JsonElement? = null,
)
