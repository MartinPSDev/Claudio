package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** An image file attached to a chat message. */
@Serializable
data class MessageImageFile(
    @SerialName("file_uuid")       val fileUuid: String? = null,
    @SerialName("file_name")       val fileName: String? = null,
    @SerialName("sanitized_name")  val sanitizedName: String? = null,
    @SerialName("size_bytes")      val sizeBytes: Long? = null,
    @SerialName("created_at")      val createdAt: String? = null,
    val path: String? = null,
    @SerialName("preview_asset")   val previewAsset: JsonElement? = null,
    @SerialName("thumbnail_asset") val thumbnailAsset: JsonElement? = null,
)

/** A document file attached to a chat message. */
@Serializable
data class MessageDocumentFile(
    @SerialName("file_uuid")        val fileUuid: String? = null,
    @SerialName("file_name")        val fileName: String? = null,
    @SerialName("sanitized_name")   val sanitizedName: String? = null,
    @SerialName("size_bytes")       val sizeBytes: Long? = null,
    @SerialName("created_at")       val createdAt: String? = null,
    val path: String? = null,
    @SerialName("document_asset")   val documentAsset: JsonElement? = null,
    @SerialName("thumbnail_asset")  val thumbnailAsset: JsonElement? = null,
)

/** A binary blob file attached to a chat message. */
@Serializable
data class MessageBlobFile(
    @SerialName("file_uuid")      val fileUuid: String? = null,
    @SerialName("file_name")      val fileName: String? = null,
    @SerialName("sanitized_name") val sanitizedName: String? = null,
    @SerialName("size_bytes")     val sizeBytes: Long? = null,
    @SerialName("created_at")     val createdAt: String? = null,
    val path: String? = null,
)
