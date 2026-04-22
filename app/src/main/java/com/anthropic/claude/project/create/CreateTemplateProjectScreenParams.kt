package com.anthropic.claude.project.create

import com.anthropic.claude.api.project.ProjectType
import kotlinx.serialization.Serializable

/** Navigation params for creating a project from a template. */
@Serializable
data class CreateTemplateProjectScreenParams(
    val projectType: ProjectType? = null,
)
