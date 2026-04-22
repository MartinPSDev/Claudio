package com.anthropic.claude.api.project

import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Full server-side representation of a Claude Project.
 * Projects group conversations, knowledge documents, and custom instructions.
 */
@Serializable
data class Project(
    val uuid: ProjectId? = null,
    val name: String? = null,
    val description: String? = null,
    @SerialName("is_private") val isPrivate: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    val creator: ProjectActorAccount? = null,
    @SerialName("archived_at") val archivedAt: String? = null,
    val archiver: ProjectActorAccount? = null,
    @SerialName("is_starred") val isStarred: Boolean = false,
    @SerialName("is_starter_project") val isStarterProject: Boolean = false,
    val type: ProjectType? = null,
    val subtype: ProjectSubtype? = null,
    @SerialName("prompt_template") val promptTemplate: String? = null,
    @SerialName("docs_count") val docsCount: Int? = null,
    @SerialName("files_count") val filesCount: Int? = null,
    val permissions: List<ProjectPermission>? = null,
    val canDelete: Boolean = false,
    val canEditSettings: Boolean = false,
)
