package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to create a new code/agent session. */
@Serializable
data class CreateSessionParams(
    val title: String? = null,
    @SerialName("session_context") val sessionContext: JsonElement? = null,
    @SerialName("environment_id")  val environmentId: String? = null,
    val events: JsonElement? = null,
)

/** Request to create a cloud execution environment. */
@Serializable
data class EnvironmentCreateRequest(
    val name: String? = null,
    val kind: String? = null,
    val description: String? = null,
    val config: JsonElement? = null,
)

/** Parameters to generate a session title and branch name. */
@Serializable
data class GenerateTitleAndBranchParams(
    @SerialName("first_session_message") val firstSessionMessage: String? = null,
)

/** Request to get status for multiple git branches. */
@Serializable
data class GetBatchBranchStatusRequest(
    @SerialName("repo_branches") val repoBranches: List<String>? = null,
    @SerialName("session_id")    val sessionId: String? = null,
)

/** Request to get or create a dispatch (agent) session. */
@Serializable
data object GetOrCreateDispatchSessionRequest

/** Parameters for a git diff/compare operation via proxy. */
@Serializable
data class GitProxyCompareRequest(
    val owner: String? = null,
    val repo: String? = null,
    val base: String? = null,
    val head: String? = null,
)

/** Parameters to re-sync a repository in the current environment. */
@Serializable
data class RepoResyncParams(
    val owner: String? = null,
    val repo: String? = null,
    @SerialName("ghe_configuration_id") val gheConfigurationId: String? = null,
)

/** Request to enable/disable auto-merge on a pull request. */
@Serializable
data class SetPrAutoMergeRequest(
    @SerialName("session_id") val sessionId: String? = null,
    val repo: String? = null,
    @SerialName("pr_number")  val prNumber: Int? = null,
    val enable: Boolean? = null,
)

/** Parameters to update an existing session (e.g. rename). */
@Serializable
data class UpdateSessionParams(val title: String? = null)
