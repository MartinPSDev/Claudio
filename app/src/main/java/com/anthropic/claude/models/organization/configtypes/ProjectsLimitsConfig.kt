package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Limits on free project creation for an organization. */
@Serializable
data class ProjectsLimitsConfig(
    @SerialName("max_free_projects") val maxFreeProjects: Int? = null,
)
