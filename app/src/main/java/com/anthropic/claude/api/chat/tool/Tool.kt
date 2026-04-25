package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Tool definition - sealed class with KnownTool and CustomTool variants.
 * Mapped from Tool.smali, Tool$KnownTool.smali, Tool$CustomTool.smali.
 */
@Serializable
sealed class Tool {

    /**
     * A known/built-in tool type (e.g., "computer", "text_editor").
     * Mapped from Tool$KnownTool.smali.
     * Fields: name (String), type (String)
     */
    @Serializable
    data class KnownTool(
        val name: String,
        val type: String
    ) : Tool()

    /**
     * A custom tool definition with full schema.
     * Mapped from Tool$CustomTool.smali.
     * Fields: name, title, description, input_schema (InputSchema)
     */
    @Serializable
    data class CustomTool(
        val name: String,
        val title: String,
        val description: String,
        @SerialName("input_schema")
        val inputSchema: InputSchema
    ) : Tool()
}

/**
 * Input schema for custom tool definitions.
 * Mapped from InputSchema.smali (21KB).
 * Represents a JSON Schema object type with properties and required fields.
 */
@Serializable
data class InputSchema(
    val type: String = "object",
    val properties: Map<String, PropertyDefinition>? = null,
    val required: List<String>? = null
)
