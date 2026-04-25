package com.anthropic.claude.types.strings

import kotlinx.serialization.Serializable

/**
 * Inline value class wrapping a message UUID string.
 *
 * Constants:
 * - ROOT_PARENT_UUID = "00000000-0000-4000-8000-000000000000"
 * - EMPTY = ""
 */
@Serializable
@JvmInline
value class MessageId(val value: String) {
    companion object {
        val ROOT_PARENT_UUID = MessageId("00000000-0000-4000-8000-000000000000")
        val EMPTY = MessageId("")
    }

    override fun toString(): String = "MessageId(value=$value)"
}

/**
 * Inline value class wrapping an account UUID string.
 */
@Serializable
@JvmInline
value class AccountId(val value: String) {
    override fun toString(): String = "AccountId(value=$value)"
}

/**
 * Inline value class wrapping an organization UUID string.
 */
@Serializable
@JvmInline
value class OrganizationId(val value: String) {
    override fun toString(): String = "OrganizationId(value=$value)"
}

/**
 * Inline value class wrapping an app session UUID string.
 */
@Serializable
@JvmInline
value class AppSessionId(val value: String) {
    override fun toString(): String = "AppSessionId(value=$value)"
}

/**
 * Inline value class wrapping an email address string.
 */
@Serializable
@JvmInline
value class EmailAddress(val value: String) {
    override fun toString(): String = "EmailAddress(value=$value)"
}

/**
 * Inline value class wrapping a style UUID string.
 */
@Serializable
@JvmInline
value class StyleId(val value: String) {
    override fun toString(): String = "StyleId(value=$value)"
}

/**
 * Inline value class wrapping an MCP server UUID string.
 */
@Serializable
@JvmInline
value class McpServerId(val value: String) {
    override fun toString(): String = "McpServerId(value=$value)"
}

/**
 * Inline value class wrapping an MCP tool approval key.
 */
@Serializable
@JvmInline
value class McpToolApprovalKey(val value: String) {
    override fun toString(): String = "McpToolApprovalKey(value=$value)"
}

/**
 * Inline value class wrapping an MCP tool key.
 */
@Serializable
@JvmInline
value class McpToolKey(val value: String) {
    override fun toString(): String = "McpToolKey(value=$value)"
}

/**
 * Thinking mode value class.
 */
@Serializable
@JvmInline
value class ThinkingMode(val value: String) {
    override fun toString(): String = "ThinkingMode(value=$value)"
}

/**
 * Research mode value class.
 */
@Serializable
@JvmInline
value class ResearchMode(val value: String) {
    override fun toString(): String = "ResearchMode(value=$value)"
}

/**
 * Tool search mode value class.
 */
@Serializable
@JvmInline
value class ToolSearchMode(val value: String) {
    override fun toString(): String = "ToolSearchMode(value=$value)"
}

/**
 * Capability value class.
 */
@Serializable
@JvmInline
value class Capability(val value: String) {
    override fun toString(): String = "Capability(value=$value)"
}
