package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A server-side environment (sandbox or remote machine) used by an agentic session.
 */
@Serializable
data class EnvironmentResource(
    val kind: EnvironmentKind? = null,
    @SerialName("environment_id") val environmentId: String? = null,
    val name: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val state: EnvironmentState? = null,
    val config: EnvironmentConfiguration? = null,
    @SerialName("bridge_info") val bridgeInfo: BridgeEnvironmentInfo? = null,
)

/** Classification of the environment type. */
@Serializable
enum class EnvironmentKind {
    @SerialName("local") LOCAL,
    @SerialName("remote") REMOTE,
    @SerialName("sandbox") SANDBOX,
}

/** Runtime state of an environment. */
@Serializable
enum class EnvironmentState {
    @SerialName("starting") STARTING,
    @SerialName("running") RUNNING,
    @SerialName("stopping") STOPPING,
    @SerialName("stopped") STOPPED,
    @SerialName("error") ERROR,
}

/** Configuration parameters for an environment. */
@Serializable
data class EnvironmentConfiguration(
    val model: String? = null,
    @SerialName("system_prompt") val systemPrompt: String? = null,
    @SerialName("max_tokens") val maxTokens: Int? = null,
    val tools: List<String>? = null,
)
