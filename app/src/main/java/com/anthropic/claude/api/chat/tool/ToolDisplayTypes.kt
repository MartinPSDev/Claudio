package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable

/**
 * JSON block display content for tool results.
 */
@Serializable
data class JsonBlockDisplayContent(
    val json: String? = null,
    val title: String? = null
) : ToolDisplayContent

/**
 * Table display content for tool results.
 */
@Serializable
data class TableDisplayContent(
    val headers: List<String>? = null,
    val rows: List<List<String>>? = null,
    val title: String? = null
) : ToolDisplayContent

/**
 * Code block display content.
 */
@Serializable
data class CodeBlockDisplayContent(
    val code: String? = null,
    val language: String? = null,
    val title: String? = null
) : ToolDisplayContent

/**
 * Plain text display content.
 */
@Serializable
data class TextDisplayContent(
    val text: String? = null
) : ToolDisplayContent

/**
 * Rich link display content.
 */
@Serializable
data class RichLinkDisplayContent(
    val url: String? = null,
    val title: String? = null,
    val description: String? = null,
    val favicon_url: String? = null
) : ToolDisplayContent

/**
 * Rich items display content.
 */
@Serializable
data class RichItemsDisplayContent(
    val items: List<RichItem>? = null
) : ToolDisplayContent

@Serializable
data class RichItem(
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val image_url: String? = null
)

/**
 * Unknown/fallback display content type.
 */
@Serializable
data class UnknownDisplayContent(
    val raw: String? = null
) : ToolDisplayContent
