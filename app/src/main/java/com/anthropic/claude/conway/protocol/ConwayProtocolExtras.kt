package com.anthropic.claude.conway.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// ─── Search ───────────────────────────────────────────────────────────────────

/**
 * A single search result hit from the Conway message history search.
 * Default role is "main" (extracted from Smali const-string).
 */
@Serializable
data class ConwaySearchHit(
    val agentId: String,
    val role: String = "main",
    val seq: Long? = null,
    val snippet: String? = null,
    val timestamp: Long? = null,
)

/**
 * A paginated response of Conway conversation messages.
 */
@Serializable
data class PaginatedMessages(
    val messages: List<JsonElement> = emptyList(),
    @SerialName("hasMore") val hasMore: Boolean = false,
    @SerialName("oldestSeq") val oldestSeq: Long? = null,
)

// ─── Error Payload ────────────────────────────────────────────────────────────

/**
 * Structured error payload sent by the Conway server on failure.
 */
@Serializable
data class ConwayErrorPayload(
    val layer: String,
    val code: String? = null,
    val message: String? = null,
    val data: JsonElement? = null,
)

// ─── Client Info ──────────────────────────────────────────────────────────────

/**
 * Lightweight info about a registered Conway client.
 */
@Serializable
data class ConwayClientInfo(
    val id: String,
    val name: String? = null,
    val tools: List<String> = emptyList(),
)

// ─── Content Blocks ───────────────────────────────────────────────────────────

/**
 * Discriminated content blocks within a Conway message.
 */
@Serializable
sealed interface ContentBlock {

    /**
     * A tool use call dispatched by the model to a server-side tool.
     */
    @Serializable
    @SerialName("server_tool_use")
    data class ServerToolUse(
        val id: String,
        val name: String,
        val input: JsonElement? = null,
    ) : ContentBlock

    /**
     * A tool use call dispatched by the model to a client-side tool.
     */
    @Serializable
    @SerialName("tool_use")
    data class ToolUse(
        val id: String,
        val name: String,
        val input: JsonElement? = null,
    ) : ContentBlock
}

// ─── Model Info ───────────────────────────────────────────────────────────────

/**
 * Model availability info for the current session.
 */
@Serializable
data class ModelInfo(
    val current: String? = null,
    val available: List<String>? = null,
)

// ─── UI Demo Destination ──────────────────────────────────────────────────────

/**
 * Navigation destinations for the internal UI demo/component gallery app.
 * Extracted (7KB).
 */
sealed interface UiDemoAppDestination {
    /** Home screen of the demo app. */
    data object Home : UiDemoAppDestination
    /** Common UI components gallery screen. */
    data object CommonComponents : UiDemoAppDestination {
        const val ROUTE = "COMMON_COMPONENTS"
        const val LABEL = "Common UI Components"
    }
}
