package com.anthropic.claude.api.chat.tool

import com.anthropic.claude.api.chat.citation.Citation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A domain that appeared frequently in Research Mode search results.
 */
@Serializable
data class TopSourceDomain(
    @SerialName("icon_url") val iconUrl: String? = null,
    val name: String? = null,
    val count: Int = 0,
)

/**
 * Full status snapshot of an ongoing or completed Research Mode task.
 */
@Serializable
data class ResearchStatusResponse(
    @SerialName("started_at") val startedAt: String? = null,
    @SerialName("total_sources") val totalSources: Int = 0,
    val error: String? = null,
    @SerialName("research_headline") val researchHeadline: String? = null,
    val status: ResearchStatus = ResearchStatus.UNKNOWN,
    @SerialName("finished_at") val finishedAt: String? = null,
    @SerialName("top_icon_urls") val topIconUrls: List<String>? = null,
    @SerialName("top_source_domains") val topSourceDomains: List<TopSourceDomain>? = null,
    @SerialName("agent_summaries") val agentSummaries: List<AgentSummary>? = null,
)

/**
 * Input payload for a tool call that creates or updates an Artifact.
 */
@Serializable
data class ArtifactToolInput(
    val id: String,
    val type: String,
    val title: String,
    val source: String? = null,
    val command: String,
    val content: String,
    @SerialName("md_citations") val mdCitations: List<Citation> = emptyList(),
)
