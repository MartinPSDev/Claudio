package com.anthropic.claude.project.details

import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDetailsScreenParams(
    val projectId: ProjectId,
    val isRootScreen: Boolean = false,
)
