package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Parameters to create a new project. */
@Serializable
data class ProjectCreateParams(
    val name: String? = null,
    val description: String? = null,
    @SerialName("prompt_template")  val promptTemplate: String? = null,
    @SerialName("is_private")       val isPrivate: Boolean? = null,
    val type: String? = null,
    val subtype: String? = null,
)

/** Parameters to update an existing project. */
@Serializable
data class ProjectUpdateParams(
    val name: String? = null,
    val description: String? = null,
    @SerialName("prompt_template")  val promptTemplate: String? = null,
    @SerialName("is_private")       val isPrivate: Boolean? = null,
    @SerialName("is_archived")      val isArchived: Boolean? = null,
    @SerialName("is_starred")       val isStarred: Boolean? = null,
)
