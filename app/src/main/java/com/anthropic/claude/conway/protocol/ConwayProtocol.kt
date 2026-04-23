package com.anthropic.claude.conway.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// ─── Client Registration ───────────────────────────────────────────────────────

/**
 * Sent by the client to register itself on the Conway WebSocket session.
 * Fields from ClientRegistrationRequest.smali (27KB).
 */
@Serializable
data class ClientRegistrationRequest(
    @SerialName("clientName") val clientName: String,
    @SerialName("clientId") val clientId: String? = null,
    @SerialName("clientDescription") val clientDescription: String? = null,
    val tools: List<String> = emptyList(),
    @SerialName("welcomeMessage") val welcomeMessage: String? = null,
)

/**
 * Server response to a successful client registration.
 * Fields from ClientRegistrationResponse.smali (24KB).
 */
@Serializable
data class ClientRegistrationResponse(
    @SerialName("clientId") val clientId: String,
    @SerialName("sessionId") val sessionId: String? = null,
    @SerialName("welcomeMessage") val welcomeMessage: String? = null,
    @SerialName("registeredTools") val registeredTools: List<String> = emptyList(),
)

// ─── Conway Extension Metadata ────────────────────────────────────────────────

/**
 * Metadata for a Conway extension (MCP server or built-in tool set).
 * Fields from ConwayExtensionMeta.smali (39KB).
 */
@Serializable
data class ConwayExtensionMeta(
    val name: String,
    @SerialName("displayName") val displayName: String? = null,
    val description: String? = null,
    @SerialName("extensionVersion") val extensionVersion: String? = null,
    @SerialName("isBuiltin") val isBuiltin: Boolean = false,
    @SerialName("hasHook") val hasHook: Boolean = false,
    val tools: List<String> = emptyList(),
    @SerialName("httpRoutes") val httpRoutes: List<String> = emptyList(),
) {
    override fun toString(): String =
        "ConwayExtensionMeta(name=$name, displayName=$displayName, extensionVersion=$extensionVersion, hasHook=$hasHook)"
}

// ─── Agent State ──────────────────────────────────────────────────────────────

/**
 * Current state of an agent within a Conway session.
 * Fields from AgentState.smali (26KB).
 */
@Serializable
data class AgentState(
    val id: String,
    val status: String = "idle",
    val label: String? = null,
    val messages: List<JsonElement>? = null,
    @SerialName("oldestSeq") val oldestSeq: Long? = null,
)

// ─── Stream Messages (Server → Client) ───────────────────────────────────────

/**
 * Discriminated union of all message types streamed from the Conway server.
 */
@Serializable
sealed interface StreamMessage {

    /** Connection acknowledgement — sent once the session is established. */
    @Serializable
    @SerialName("connected")
    data class Connected(
        val seq: Long,
        val ts: Long? = null,
        @SerialName("clientId") val clientId: String? = null,
        @SerialName("sessionId") val sessionId: String? = null,
        @SerialName("resumeToken") val resumeToken: String? = null,
    ) : StreamMessage

    /** Agents list has been updated. */
    @Serializable
    @SerialName("agentsUpdated")
    data class AgentsUpdated(
        val seq: Long,
        val ts: Long? = null,
        @SerialName("agentIds") val agentIds: List<String> = emptyList(),
        val agents: List<AgentState> = emptyList(),
    ) : StreamMessage

    /** A tool call dispatched by the model to the client. */
    @Serializable
    @SerialName("tool_call")
    data class ToolCall(
        val seq: Long,
        val ts: Long? = null,
        @SerialName("requestId") val requestId: String,
        @SerialName("toolName") val toolName: String,
        val input: JsonElement? = null,
    ) : StreamMessage
}

// ─── Rich Messages ────────────────────────────────────────────────────────────

/**
 * Enriched conversation message with token counts and timestamps.
 */
@Serializable
sealed interface RichMessage {

    /** An assistant turn with content blocks. */
    @Serializable
    @SerialName("assistant")
    data class Assistant(
        val id: String,
        val content: JsonElement? = null,
        val timestamp: Long? = null,
        @SerialName("tokenCount") val tokenCount: Int? = null,
    ) : RichMessage

    /** A user turn with message parts. */
    @Serializable
    @SerialName("user")
    data class User(
        val id: String,
        val parts: JsonElement? = null,
        val timestamp: Long? = null,
        @SerialName("tokenCount") val tokenCount: Int? = null,
    ) : RichMessage

    /** A status / system message (e.g. tool result, thinking). */
    @Serializable
    @SerialName("status")
    data class Status(
        val id: String,
        val subtype: String? = null,
        val content: JsonElement? = null,
        val timestamp: Long? = null,
    ) : RichMessage
}

// ─── Tool Result ──────────────────────────────────────────────────────────────

/**
 * Result returned by the client after executing a tool call.
 */
@Serializable
data class ToolResultMessage(
    val type: String = "tool_result",
    @SerialName("requestId") val requestId: String,
    val result: JsonElement? = null,
    val success: Boolean = true,
    val error: String? = null,
)

// ─── Container Health ─────────────────────────────────────────────────────────

/**
 * Health check response from a Conway container (remote code execution env).
 */
@Serializable
data class ContainerHealthResponse(
    val name: String,
    val version: String? = null,
    val protocol: String? = null,
    val endpoints: List<String> = emptyList(),
)
