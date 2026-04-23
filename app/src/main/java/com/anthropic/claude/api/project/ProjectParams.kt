package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for creating a new project.
 */
@Serializable
data class ProjectCreateParams(
    val name: String,
    val description: String? = null,
    val type: String? = null,
    val subtype: String? = null,
    @SerialName("is_private") val isPrivate: Boolean? = null,
    @SerialName("prompt_template") val promptTemplate: String? = null,
)

/**
 * Request body for updating an existing project.
 */
@Serializable
data class ProjectUpdateParams(
    val name: String? = null,
    val description: String? = null,
    @SerialName("prompt_template") val promptTemplate: String? = null,
    @SerialName("is_private") val isPrivate: Boolean? = null,
    @SerialName("is_starred") val isStarred: Boolean? = null,
    @SerialName("is_archived") val isArchived: Boolean? = null,
)
