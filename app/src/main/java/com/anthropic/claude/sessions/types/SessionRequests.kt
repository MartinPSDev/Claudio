package com.anthropic.claude.sessions.types

import kotlinx.serialization.Serializable

/**
 * Parameters for generating a title and branch for a session.
 */
@Serializable
data class GenerateTitleAndBranchParams(
    val session_id: String? = null,
    val prompt: String? = null
)

/**
 * Request to get or create a dispatch session.
 */
@Serializable
data class GetOrCreateDispatchSessionRequest(
    val project_id: String? = null,
    val model: String? = null
)

/**
 * Request to get batch branch status.
 */
@Serializable
data class GetBatchBranchStatusRequest(
    val session_id: String? = null,
    val branch_ids: List<String>? = null,
    val include_metadata: Boolean? = null
)

/**
 * Request for Git proxy compare.
 */
@Serializable
data class GitProxyCompareRequest(
    val session_id: String? = null,
    val base_ref: String? = null,
    val head_ref: String? = null,
    val include_diff: Boolean? = null
)

/**
 * Parameters for repository resync.
 */
@Serializable
data class RepoResyncParams(
    val session_id: String? = null,
    val repo_url: String? = null,
    val branch: String? = null,
    val force: Boolean? = null
)

/**
 * Request to set PR auto-merge.
 */
@Serializable
data class SetPrAutoMergeRequest(
    val session_id: String? = null,
    val pr_number: Int? = null,
    val enabled: Boolean? = null,
    val merge_method: String? = null
)

/**
 * Parameters for updating a session.
 */
@Serializable
data class UpdateSessionParams(
    val session_id: String? = null,
    val title: String? = null,
    val status: String? = null
)
