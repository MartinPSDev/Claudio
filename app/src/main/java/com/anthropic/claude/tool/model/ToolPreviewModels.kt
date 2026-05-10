package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Source for a knowledge document used by tools.
 */
@Serializable
data class KnowledgeSource(
    val type: String? = null,
    val title: String? = null,
    val url: String? = null,
    @SerialName("document_id") val documentId: String? = null,
)

/**
 * Preview data for a location shown in tool results.
 */
@Serializable
data class LocationPreviewData(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    val name: String? = null,
    @SerialName("place_id") val placeId: String? = null,
)

/**
 * Preview metadata for a mobile app tool invocation.
 */
@Serializable
data class MobileAppToolPreviewInfo(
    @SerialName("tool_name") val toolName: String? = null,
    @SerialName("display_type") val displayType: String? = null,
    @SerialName("preview_text") val previewText: String? = null,
    @SerialName("preview_image_url") val previewImageUrl: String? = null,
)

/**
 * Generic preview data for tool results.
 */
@Serializable
data class PreviewData(
    val type: String? = null,
    val title: String? = null,
    val content: String? = null,
    val url: String? = null,
    val location: LocationPreviewData? = null,
)

/**
 * Image source for tool inputs.
 */
@Serializable
data class SourceImage(
    val type: String,
    val url: String? = null,
    val text: String? = null,
    @SerialName("google_doc_id") val googleDocId: String? = null,
) {
    companion object {
        fun fromUrl(url: String) = SourceImage(type = "url", url = url)
        fun fromText(text: String) = SourceImage(type = "text", text = text)
        fun fromGoogleDoc(docId: String) = SourceImage(type = "google_doc", googleDocId = docId)
    }
}

/**
 * Input for the MCP registry search tool.
 */
@Serializable
data class SearchMcpRegistryInput(
    val query: String,
    @SerialName("max_results") val maxResults: Int = 10,
)

/**
 * Input for the general search tool.
 */
@Serializable
data class SearchToolInput(
    val query: String,
    val locale: String? = null,
    @SerialName("max_results") val maxResults: Int? = null,
    @SerialName("time_range") val timeRange: String? = null,
)
