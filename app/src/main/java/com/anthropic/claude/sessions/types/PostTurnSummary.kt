package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Summary generated after each turn in an agentic session.
 * Used to show progress and status in the Tasks UI.
 */
@Serializable
data class PostTurnSummary(
    @SerialName("status_category") val statusCategory: String? = null,
    @SerialName("status_detail") val statusDetail: String? = null,
    @SerialName("is_noteworthy") val isNoteworthy: Boolean = false,
    val title: String? = null,
    @SerialName("recent_action") val recentAction: String? = null,
    @SerialName("needs_action") val needsAction: String? = null,
    val description: String? = null,
    @SerialName("artifact_urls") val artifactUrls: List<String> = emptyList(),
)
