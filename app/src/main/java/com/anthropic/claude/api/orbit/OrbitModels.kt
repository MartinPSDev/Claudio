package com.anthropic.claude.api.orbit

import com.anthropic.claude.types.strings.ChatId
import com.anthropic.claude.types.strings.InsightId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ─── Enums ───────────────────────────────────────────────────────────────────

@Serializable
enum class OrbitInsightStatus {
    @SerialName("pending") PENDING,
    @SerialName("ready") READY,
    @SerialName("dismissed") DISMISSED,
    @SerialName("acted") ACTED,
    @SerialName("expired") EXPIRED,
    @SerialName("unknown") UNKNOWN,
}

@Serializable
enum class OrbitBriefingInsightKind {
    @SerialName("tip") TIP,
    @SerialName("follow_up") FOLLOW_UP,
    @SerialName("summary") SUMMARY,
    @SerialName("recommendation") RECOMMENDATION,
    @SerialName("unknown") UNKNOWN,
}

@Serializable
enum class OrbitBriefingActionKind {
    @SerialName("continue_conversation") CONTINUE_CONVERSATION,
    @SerialName("new_conversation") NEW_CONVERSATION,
    @SerialName("unknown") UNKNOWN,
}

@Serializable
enum class OrbitActionUrgency {
    @SerialName("low") LOW,
    @SerialName("medium") MEDIUM,
    @SerialName("high") HIGH,
    @SerialName("unknown") UNKNOWN,
}

@Serializable
enum class OrbitSentiment {
    @SerialName("positive") POSITIVE,
    @SerialName("negative") NEGATIVE,
    @SerialName("unknown") UNKNOWN,
}

// ─── Data classes ─────────────────────────────────────────────────────────────

/**
 * A suggested action embedded inside an [OrbitBriefingInsight].
 */
@Serializable
data class OrbitBriefingAction(
    @SerialName("action_uuid") val actionUuid: String? = null,
    val kind: OrbitBriefingActionKind? = null,
    val title: String? = null,
    val prompt: String? = null,
    @SerialName("conversation_uuid") val conversationUuid: ChatId? = null,
)

/**
 * User feedback recorded for an orbit insight or action.
 */
@Serializable
data class OrbitFeedback(
    val sentiment: OrbitSentiment? = null,
    val comment: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
)

/**
 * A single AI-generated insight inside a daily briefing.
 */
@Serializable
data class OrbitBriefingInsight(
    @SerialName("insight_uuid") val insightUuid: InsightId? = null,
    val kind: OrbitBriefingInsightKind? = null,
    val name: String? = null,
    val context: String? = null,
    val suggestion: String? = null,
    val source: String? = null,
    val why: String? = null,
    val actions: List<OrbitBriefingAction> = emptyList(),
    val feedback: OrbitFeedback? = null,
)

/**
 * A daily briefing container holding status, preamble text, and a list of insights.
 */
@Serializable
data class OrbitBriefing(
    val status: OrbitInsightStatus? = null,
    @SerialName("briefing_uuid") val briefingUuid: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val preamble: String? = null,
    val insights: List<OrbitBriefingInsight> = emptyList(),
)

/**
 * A standalone proactive action card (separate from briefings).
 */
@Serializable
data class OrbitAction(
    @SerialName("action_uuid") val actionUuid: String? = null,
    val title: String? = null,
    val prompt: String? = null,
    val urgency: OrbitActionUrgency? = null,
    val source: String? = null,
)

/**
 * A individual insight linked to a specific conversation.
 */
@Serializable
data class OrbitInsight(
    val status: OrbitInsightStatus? = null,
    @SerialName("conversation_uuid") val conversationUuid: ChatId? = null,
    val name: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val briefing: String? = null,
)

/**
 * Paginated response for the briefings endpoint.
 */
@Serializable
data class OrbitBriefingsResponse(
    val briefings: List<OrbitBriefing>? = null,
    @SerialName("has_more") val hasMore: Boolean = false,
    @SerialName("next_cursor") val nextCursor: String? = null,
)

/**
 * Paginated response for the recent-insights endpoint.
 */
@Serializable
data class OrbitRecentInsightsResponse(
    val insights: List<OrbitInsight>? = null,
    @SerialName("has_more") val hasMore: Boolean = false,
    @SerialName("next_cursor") val nextCursor: String? = null,
)
