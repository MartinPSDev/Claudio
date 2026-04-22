package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Information about the local bridge environment (machine, git repo, sessions).
 * Sent by the Claude Code bridge when establishing a session connection.
 */
@Serializable
data class BridgeEnvironmentInfo(
    @SerialName("max_sessions") val maxSessions: Int? = null,
    @SerialName("machine_name") val machineName: String? = null,
    val directory: String? = null,
    val branch: String? = null,
    @SerialName("git_repo_url") val gitRepoUrl: String? = null,
    val online: Boolean? = null,
    @SerialName("spawn_mode") val spawnMode: BridgeSpawnMode? = null,
)

/** Spawn strategy used by the bridge when creating a new agent session. */
@Serializable
enum class BridgeSpawnMode {
    @SerialName("auto") AUTO,
    @SerialName("manual") MANUAL,
    @SerialName("disabled") DISABLED,
}

/**
 * Details about a permission action that requires user approval.
 */
@Serializable
data class RequiresActionDetails(
    @SerialName("tool_name") val toolName: String? = null,
    @SerialName("action_description") val actionDescription: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    @SerialName("tool_use_id") val toolUseId: String? = null,
    val input: Map<String, String>? = null,
    val isToolPermission: Boolean = false,
)
