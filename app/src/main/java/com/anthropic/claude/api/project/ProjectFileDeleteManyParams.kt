package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Parameters for bulk-deleting files from a project knowledge base.
 */
@Serializable
data class ProjectFileDeleteManyParams(
    @SerialName("file_uuids") val fileUuids: List<String>? = null,
)
