package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Status of a Research Mode (compass) task.
 * Progresses from STARTING → PLANNING → SEARCHING → COMPLETED/FAILED.
 */
@Serializable
enum class ResearchStatus {
    @SerialName("UNKNOWN") UNKNOWN,
    @SerialName("STARTING") STARTING,
    @SerialName("PLANNING") PLANNING,
    @SerialName("SEARCHING") SEARCHING,
    @SerialName("INITIATING_AGENTS") INITIATING_AGENTS,
    @SerialName("CREATING_ARTIFACT") CREATING_ARTIFACT,
    @SerialName("COMPLETED") COMPLETED,
    @SerialName("CANCELLED") CANCELLED,
    @SerialName("TIMED_OUT") TIMED_OUT,
    @SerialName("FAILED") FAILED,
    @SerialName("CLIENT_TIMEOUT") CLIENT_TIMEOUT,
}

/**
 * Summary shown in the UI after a Research Mode agent run completes.
 */
@Serializable
data class AgentSummary(
    val description: String,
    @SerialName("total_sources") val totalSources: Int = 0,
    @SerialName("top_icon_urls") val topIconUrls: List<String> = emptyList(),
    @SerialName("started_at") val startedAt: String? = null,
    @SerialName("completed_at") val completedAt: String? = null,
)

/**
 * Metadata about a source page retrieved during Research Mode.
 */
@Serializable
data class SourceMetadata(
    val url: String? = null,
    val title: String? = null,
    @SerialName("favicon_url") val faviconUrl: String? = null,
    @SerialName("site_name") val siteName: String? = null,
)
