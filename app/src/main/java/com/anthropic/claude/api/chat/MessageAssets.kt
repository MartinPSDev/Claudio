package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.MessageId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An image attached to a chat message — stored as a CDN URL with optional dimensions.
 */
@Serializable
data class MessageImageAsset(
    val url: String,
    @SerialName("image_width") val imageWidth: Int? = null,
    @SerialName("image_height") val imageHeight: Int? = null,
    val messageId: MessageId? = null,
)

/**
 * A document (PDF, etc.) attached to a chat message.
 */
@Serializable
data class MessageDocumentAsset(
    val url: String,
    @SerialName("page_count") val pageCount: Int? = null,
)

/**
 * Marker interface for any file type attached to a message.
 * Implemented by [MessageUnknownFile] and potentially other concrete asset types.
 */
interface MessageFile {
    val fileUuid: com.anthropic.claude.types.strings.FileId
}

/**
 * A file attachment of an unrecognised/unsupported MIME type.
 */
@Serializable
data class MessageUnknownFile(
    @SerialName("file_uuid") override val fileUuid: com.anthropic.claude.types.strings.FileId,
    @SerialName("file_name") val fileName: String? = null,
) : MessageFile
