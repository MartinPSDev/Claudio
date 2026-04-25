package com.anthropic.claude.api.chat

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Sealed interface for message file attachments.
 * Discriminated by "file_kind" field.
 *
 * Subtypes: MessageBlobFile, MessageDocumentFile, MessageImageFile, MessageUnknownFile
 */
@Serializable
@JsonClassDiscriminator("file_kind")
sealed interface MessageFile {
    val file_uuid: String
}
