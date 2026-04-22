package com.anthropic.claude.api.project

import com.anthropic.claude.types.strings.ProjectId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

/**
 * A document attached to a project's knowledge base.
 * Uploaded by the user and indexed for retrieval-augmented generation.
 */
@Serializable
data class ProjectDoc(
    val uuid: String? = null,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("project_uuid") val projectUuid: ProjectId? = null,
)

/**
 * Storage and search statistics for a project's knowledge base.
 */
@Serializable
data class ProjectKnowledgeStats(
    @SerialName("knowledge_size") val knowledgeSize: Long = 0L,
    @SerialName("max_knowledge_size") val maxKnowledgeSize: Long = 0L,
    @SerialName("project_knowledge_search_threshold") val projectKnowledgeSearchThreshold: Long? = null,
    @SerialName("use_project_knowledge_search") val useProjectKnowledgeSearch: Boolean = false,
)
