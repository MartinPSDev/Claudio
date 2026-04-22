package com.anthropic.claude.sessions.types

import com.anthropic.claude.types.strings.SessionId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Full server-side representation of an agentic session (Claude Code / Computer Use).
 * This is the primary domain object returned by the sessions API.
 */
@Serializable
data class SessionResource(
    val type: String? = null,
    val id: SessionId? = null,
    val title: String? = null,
    @SerialName("session_status") val sessionStatus: SessionStatus? = null,
    @SerialName("environment_id") val environmentId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("session_context") val sessionContext: SessionContext? = null,
    @SerialName("bridge_spawn_path") val bridgeSpawnPath: String? = null,
    @SerialName("connection_status") val connectionStatus: ConnectionStatus? = null,
    @SerialName("worker_status") val workerStatus: WorkerStatus? = null,
    @SerialName("client_presence") val clientPresence: List<ClientPresenceInfo>? = null,
    @SerialName("trigger_id") val triggerId: String? = null,
    val origin: String? = null,
    @SerialName("post_turn_summary") val postTurnSummary: PostTurnSummary? = null,
    @SerialName("task_summary") val taskSummary: String? = null,
    @SerialName("external_metadata") val externalMetadata: SessionExternalMetadata? = null,
)

/** Lifecycle status of an agentic session. */
@Serializable
enum class SessionStatus {
    @SerialName("created") CREATED,
    @SerialName("starting") STARTING,
    @SerialName("running") RUNNING,
    @SerialName("paused") PAUSED,
    @SerialName("completed") COMPLETED,
    @SerialName("error") ERROR,
    @SerialName("cancelled") CANCELLED,
}

/** Real-time connection status between the app and the bridge. */
@Serializable
enum class ConnectionStatus {
    @SerialName("connected") CONNECTED,
    @SerialName("disconnected") DISCONNECTED,
    @SerialName("reconnecting") RECONNECTING,
}

/** Status of the background worker running the session. */
@Serializable
enum class WorkerStatus {
    @SerialName("idle") IDLE,
    @SerialName("running") RUNNING,
    @SerialName("stopped") STOPPED,
    @SerialName("error") ERROR,
}

/** Presence info for a connected client in the session. */
@Serializable
data class ClientPresenceInfo(
    @SerialName("client_id") val clientId: String? = null,
    @SerialName("connected_at") val connectedAt: String? = null,
)

/** External metadata attached to a session from the originating surface. */
@Serializable
data class SessionExternalMetadata(
    val source: String? = null,
    @SerialName("chat_id") val chatId: String? = null,
    @SerialName("org_id") val orgId: String? = null,
)
