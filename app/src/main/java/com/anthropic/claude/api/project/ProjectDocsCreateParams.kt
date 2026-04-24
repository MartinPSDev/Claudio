package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Parameters for creating a new document in a project knowledge base.
 */
@Serializable
data class ProjectDocsCreateParams(
    @SerialName("file_name") val fileName: String? = null,
    val content: String? = null,
)
