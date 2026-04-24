package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request for proxying a git file read through the remote session environment.
 */
@Serializable
data class GitProxyFileRequest(
    val owner: String? = null,
    val repo: String? = null,
    val path: String? = null,
    val ref: String? = null,
)
