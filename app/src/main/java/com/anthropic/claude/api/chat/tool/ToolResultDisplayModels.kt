package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Metadata for a Google Doc attached or referenced in a tool result.
 */
@Serializable
data class GoogleDocMetadata(
    @SerialName("doc_uuid") val docUuid: String? = null,
    val owner: String? = null,
)

/**
 * Tabular display content from a tool result.
 */
@Serializable
data class TableDisplayContent(
    val table: JsonElement? = null,
)

/**
 * An image gallery from a tool result.
 */
@Serializable
data class ToolResultImageGallery(
    val images: List<JsonElement>? = null,
)

/**
 * Plain text content from a tool result, with an optional UUID for tracking.
 */
@Serializable
data class ToolResultText(
    val text: String? = null,
    val uuid: String? = null,
)
