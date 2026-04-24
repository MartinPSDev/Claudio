package com.anthropic.claude.chat.bottomsheet

/**
 * Bottom sheet that shows a preview of a URL/link from a message.
 */
data class PreviewLink(
    val source: String? = null,
    val url: String? = null,
)
