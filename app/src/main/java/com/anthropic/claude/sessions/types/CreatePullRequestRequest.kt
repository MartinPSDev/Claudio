package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to create a pull request from a remote code session. */
@Serializable
data class CreatePullRequestRequest(
    val repo: String? = null,
    val title: String? = null,
    val body: String? = null,
    val head: String? = null,
    val base: String? = null,
    val draft: Boolean? = null,
)
