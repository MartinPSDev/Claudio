package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.FileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageAttachment(
    val id: FileId? = null,
    @SerialName("file_name") val fileName: String,
    @SerialName("file_size") val fileSize: Int,
    @SerialName("file_type") val fileType: String,
    @SerialName("extracted_content") val extractedContent: String = "",
)
