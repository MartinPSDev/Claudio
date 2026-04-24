package com.anthropic.claude.api.chat

import kotlinx.serialization.Serializable

/**
 * Represents a message file union type.
 * Concrete subtypes: MessageImageFile, MessageDocumentFile, MessageBlobFile.
 */
sealed interface MessageFile

/** Rendering mode for chat messages. */
@Serializable
enum class RenderingMode {
    MESSAGES;

    companion object {
        val DEFAULT = MESSAGES
    }
}
