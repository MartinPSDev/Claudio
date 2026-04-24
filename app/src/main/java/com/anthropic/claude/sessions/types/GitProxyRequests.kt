package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to get the status of multiple git branches in a session.
 */
@Serializable
data class GetBatchBranchStatusRequest(
    @SerialName("repo_branches") val repoBranches: List<JsonElement>,
    @SerialName("session_id") val sessionId: String? = null,
)

/**
 * Request for a git diff/compare between two refs through the proxy.
 */
@Serializable
data class GitProxyCompareRequest(
    val owner: String? = null,
    val repo: String? = null,
    val base: String? = null,
    val head: String? = null,
)
