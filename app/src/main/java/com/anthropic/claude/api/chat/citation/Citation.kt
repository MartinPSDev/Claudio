package com.anthropic.claude.api.chat.citation

import kotlinx.serialization.Serializable

/**
 * A citation reference within a message content block.
 */
@Serializable
data class Citation(
    val type: String? = null,
    val title: String? = null,
    val url: String? = null,
    val start_char_index: Int? = null,
    val end_char_index: Int? = null,
    val cited_text: String? = null,
    val document_index: Int? = null,
    val document_title: String? = null
)
