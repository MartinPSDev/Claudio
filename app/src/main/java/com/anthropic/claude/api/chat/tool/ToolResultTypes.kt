package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable

/**
 * Tool result containing text content.
 */
@Serializable
data class ToolResultText(
    val text: String? = null
) : ToolResultContent

/**
 * Tool result containing an image gallery.
 */
@Serializable
data class ToolResultImageGallery(
    val images: List<ToolResultImage>? = null
) : ToolResultContent

/**
 * Tool result containing a single image.
 */
@Serializable
data class ToolResultImage(
    val url: String? = null,
    val media_type: String? = null,
    val data: String? = null,
    val title: String? = null
) : ToolResultContent

/**
 * Tool result containing knowledge/reference content.
 */
@Serializable
data class ToolResultKnowledge(
    val content: String? = null,
    val source: String? = null
) : ToolResultContent

/**
 * Unknown/fallback tool result content type.
 */
@Serializable
data class ToolResultUnknown(
    val raw: String? = null
) : ToolResultContent

/**
 * Generic source metadata for citations.
 */
@Serializable
data class GenericSourceMetadata(
    val title: String? = null,
    val url: String? = null,
    val snippet: String? = null
) : SourceMetadata

/**
 * Google Doc source metadata for citations.
 */
@Serializable
data class GoogleDocMetadata(
    val title: String? = null,
    val url: String? = null,
    val document_id: String? = null,
    val mime_type: String? = null
) : SourceMetadata

/**
 * Webpage source metadata for citations.
 */
@Serializable
data class WebpageMetadata(
    val title: String? = null,
    val url: String? = null,
    val favicon_url: String? = null
) : SourceMetadata

/**
 * Unknown/fallback source metadata type.
 */
@Serializable
data class UnknownSourceMetadata(
    val raw: String? = null
) : SourceMetadata
