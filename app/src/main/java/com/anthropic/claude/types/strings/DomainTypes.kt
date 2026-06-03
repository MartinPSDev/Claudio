package com.anthropic.claude.types.strings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// =========================================================================
// Core identifier value classes
// =========================================================================

@JvmInline
value class ChatId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ProjectId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ThinkingMode(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ResearchMode(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class AccountId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class OrganizationId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class MessageId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class StyleId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class EmailAddress(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class McpServerId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class DirectoryServerId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class SessionId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ToolUseId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class TaskId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ArtifactId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class PublishedArtifactId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ArtifactIdentifier(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class SessionKey(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ResearchTaskId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class VoiceSessionId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ChatSnapshotId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ProjectDocId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class EnvironmentId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class McpToolKey(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class ConversationCursor(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class InsightId(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class FileId(val value: String) {
    override fun toString(): String = "FileId(value=$value)"
}

// =========================================================================
// Server-Localized String (i18n model from API)
// =========================================================================

/**
 * Represents a server-provided localized string with English fallback.
 * Used throughout the API for user-facing text that may have translations.
 *
 * Decompiled from: _ServerLocalizedString.smali
 */
@Serializable
data class _ServerLocalizedString(
    val english: String,
    val translation: String? = null
) {
    /** Returns the translation if available, otherwise falls back to english. */
    val localizedText: String
        get() = translation ?: english
}
