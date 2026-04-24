package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Parameters for creating a new remote code execution session.
 */
@Serializable
data class CreateSessionParams(
    val events: List<JsonElement>? = null,
    val title: String? = null,
    @SerialName("environment_id") val environmentId: String? = null,
    @SerialName("session_context") val sessionContext: JsonElement? = null,
)

/**
 * Request body to create a new execution environment.
 */
@Serializable
data class EnvironmentCreateRequest(
    val name: String? = null,
    val description: String? = null,
    val kind: String? = null,
    val config: JsonElement? = null,
)

/**
 * Request body to create a pull request from the remote session.
 */
@Serializable
data class CreatePullRequestRequest(
    val repo: String,
    val title: String? = null,
    val body: String? = null,
    val head: String? = null,
    val base: String? = null,
    val draft: Boolean = false,
)
