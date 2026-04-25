package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable

/**
 * A single item in an image gallery tool result.
 */
@Serializable
data class ImageGalleryItem(
    val id: ImageGalleryItemId? = null,
    val url: String? = null,
    val title: String? = null,
    val source: String? = null,
    val page_url: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val thumbnail_url: String? = null
)

/**
 * Typed ID for image gallery items.
 */
@JvmInline
@Serializable
value class ImageGalleryItemId(val value: String)

/**
 * Partial JSON input for tool use (streamed incrementally).
 */
@Serializable
data class ToolUseInput(
    val partial_json: String? = null
)
