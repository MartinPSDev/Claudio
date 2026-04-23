package com.anthropic.claude.chat.parse

import kotlinx.serialization.json.JsonElement

/**
 * Identifies a parsed content block by its parent message and position index.
 */
data class ParsedContentBlockId(
    val messageId: String,
    val index: Int = 0,
)

/**
 * A parsed content block within a chat message.
 */
sealed interface ParsedContentBlock {

    /**
     * An MCP tool invocation block rendered in the message list.
     */
    data class McpToolInvocation(
        val id: String,
        val toolName: String? = null,
        val integrationName: String? = null,
        val integrationIconUrl: String? = null,
        val iconName: String? = null,
        val inputDisplayContent: String? = null,
        val outputDisplayContent: String? = null,
        val messageText: String? = null,
        val isComplete: Boolean = false,
        val isError: Boolean = false,
        val flags: Int = 0,
    ) : ParsedContentBlock

    /**
     * An extended-thinking block — shows Claude's reasoning process.
     */
    data class Thinking(
        val id: String,
        val body: String? = null,
        val latestSummary: String? = null,
        val markdownRoot: JsonElement? = null,
        val startTimestamp: Long? = null,
        val endTimestamp: Long? = null,
        val flags: Int = 0,
    ) : ParsedContentBlock
}
