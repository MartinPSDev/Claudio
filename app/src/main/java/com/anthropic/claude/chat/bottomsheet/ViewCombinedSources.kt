package com.anthropic.claude.chat.bottomsheet

import kotlinx.serialization.json.JsonElement

/**
 * Bottom sheet to display combined citation sources for a message.
 */
data class ViewCombinedSources(
    val citations: JsonElement? = null,
    val otherSources: JsonElement? = null,
)
