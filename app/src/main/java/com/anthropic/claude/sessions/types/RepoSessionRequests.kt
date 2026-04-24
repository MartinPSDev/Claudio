package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request to enable or disable auto-merge for a pull request in the session.
 */
@Serializable
data class SetPrAutoMergeRequest(
    @SerialName("session_id") val sessionId: String? = null,
    val repo: String? = null,
    @SerialName("pr_number") val prNumber: Int? = null,
    val enable: Boolean = false,
)

/**
 * Parameters for triggering a repository re-sync in the remote session.
 */
@Serializable
data class RepoResyncParams(
    val owner: String? = null,
    val repo: String? = null,
    @SerialName("ghe_configuration_id") val gheConfigurationId: String? = null,
)
