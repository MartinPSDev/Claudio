package com.anthropic.claude.project.create

import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.Serializable

/** Opaque identifier for a project template. */
@JvmInline
@Serializable
value class ProjectTemplateId(val value: String) {
    override fun toString(): String = value
}

/** Navigation params for the Upload Materials step in project creation. */
@Serializable
data class UploadMaterialsScreenParams(
    val projectId: ProjectId,
    val templateId: ProjectTemplateId? = null,
)
