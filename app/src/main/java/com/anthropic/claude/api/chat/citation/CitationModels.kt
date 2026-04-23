package com.anthropic.claude.api.chat.citation

import com.anthropic.claude.api.chat.tool.SourceMetadata
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A web/document source associated with a [Citation].
 */
@Serializable
data class CitationSource(
    val url: String? = null,
    val title: String? = null,
    val subtitles: List<String>? = null,
    @SerialName("icon_url") val iconUrl: String? = null,
    val source: String? = null,
    @SerialName("resource_type") val resourceType: String? = null,
    @SerialName("content_body") val contentBody: String? = null,
)

/**
 * A citation linking a passage in a model response to one or more web sources.
 * Used in Research Mode (compass_mode) responses.
 */
@Serializable
data class Citation(
    val uuid: String? = null,
    val title: String? = null,
    val sources: List<CitationSource>? = null,
    @SerialName("is_trusted") val isTrusted: Boolean? = null,
    @SerialName("start_index") val startIndex: Int? = null,
    @SerialName("end_index") val endIndex: Int? = null,
    val url: String? = null,
    val subtitles: List<String>? = null,
    val metadata: SourceMetadata? = null,
)
