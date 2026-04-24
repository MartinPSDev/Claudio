package com.anthropic.claude.types.strings

import kotlinx.serialization.Serializable

/** Inline value class wrapping a typed MCP tool key. */
@Serializable
@JvmInline
value class McpToolKey(val value: String)

/** Inline value class for an MCP tool approval key. */
@Serializable
@JvmInline
value class McpToolApprovalKey(val value: String)

/** Inline value class for an MCP server identifier. */
@Serializable
@JvmInline
value class McpServerId(val value: String)

/** Inline value class for a chat message identifier (UUID). */
@Serializable
@JvmInline
value class MessageId(val value: String) {
    companion object {
        /** Sentinel null-ish message ID. */
        val EMPTY = MessageId("00000000-0000-4000-8000-000000000000")
    }
}

/** Inline value class for a conversation style identifier. */
@Serializable
@JvmInline
value class StyleId(val value: String)

/** Inline value class for a validated email address. */
@Serializable
@JvmInline
value class EmailAddress(val value: String)

/** Inline value class for the thinking mode (e.g. "normal"). */
@Serializable
@JvmInline
value class ThinkingMode(val value: String) {
    companion object {
        val NORMAL = ThinkingMode("normal")
    }
}
