package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** JSON Schema object-type property for tool input definitions. */
@Serializable
data class ObjectProperty(
    val description: String? = null,
    val properties: JsonElement? = null,
    val required: List<String>? = null,
)

/** JSON Schema input schema for a tool. */
@Serializable
data class InputSchema(
    val type: String? = null,
    val properties: JsonElement? = null,
    val required: List<String>? = null,
)

/** Google Doc metadata attached to a tool result. */
@Serializable
data class GoogleDocMetadata(
    @SerialName("doc_uuid") val docUuid: String? = null,
    val owner: String? = null,
)

/** A gallery of tool-result images. */
@Serializable
data class ToolResultImageGallery(val images: JsonElement? = null)

/** Plain-text tool result with optional UUID. */
@Serializable
data class ToolResultText(
    val text: String? = null,
    val uuid: String? = null,
)

/** Table display content block for tool output. */
@Serializable
data class TableDisplayContent(val table: JsonElement? = null)

/** JSON block display content for tool output. */
@Serializable
data class JsonBlockDisplayContent(
    @SerialName("json_block") val jsonBlock: JsonElement? = null,
)

/** A custom tool definition with name, schema, and description. */
@Serializable
data class CustomToolDefinition(
    val name: String? = null,
    val title: String? = null,
    val description: String? = null,
    @SerialName("input_schema") val inputSchema: InputSchema? = null,
)

/** Integer-type JSON Schema property. */
@Serializable
data class IntegerProperty(
    val description: String? = null,
    val minimum: Int? = null,
    val maximum: Int? = null,
)

/** Number-type JSON Schema property. */
@Serializable
data class NumberProperty(
    val description: String? = null,
    val minimum: Double? = null,
    val maximum: Double? = null,
)
