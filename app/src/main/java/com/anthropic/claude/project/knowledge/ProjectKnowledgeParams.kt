package com.anthropic.claude.project.knowledge

import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.Serializable

/** Navigation params for the Project Knowledge (uploaded files) screen. */
@Serializable
data class ProjectKnowledgeScreenParams(
    val projectId: ProjectId,
)

/** Params for the delete-file confirmation dialog within a project. */
@Serializable
data class DeleteProjectFileAlertDialogParams(
    val projectId: ProjectId,
    val fileId: FileId,
)

/** Params for the delete-document confirmation dialog within a project. */
@Serializable
data class DeleteProjectDocAlertDialogParams(
    val projectId: ProjectId,
    val docId: ProjectDocId,
)

/** Opaque identifier for a file attached to a project. */
@JvmInline
@Serializable
value class FileId(val value: String) {
    override fun toString(): String = value
}

/** Opaque identifier for a project document (knowledge base entry). */
@JvmInline
@Serializable
value class ProjectDocId(val value: String) {
    override fun toString(): String = value
}
