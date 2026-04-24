package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rich link display content for rendering a preview card for a URL.
 */
@Serializable
data class RichLinkDisplayContent(
    val link: String? = null,
    @SerialName("is_trusted") val isTrusted: Boolean = false,
)
