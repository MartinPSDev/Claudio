package com.anthropic.claude.app

import com.anthropic.claude.project.create.UploadMaterialsScreenParams
import com.anthropic.claude.project.details.ProjectDetailsScreenParams
import com.anthropic.claude.project.knowledge.ProjectKnowledgeParams

/**
 * Extended navigation destinations for the Claude app — project-related deep links.
 */
sealed interface ClaudeAppProjectDestination {

    sealed interface Detail : ClaudeAppProjectDestination {
        data class TemplateUploadMaterialScreen(
            val params: UploadMaterialsScreenParams? = null,
        ) : Detail

        data class ProjectDetails(
            val params: ProjectDetailsScreenParams? = null,
        ) : Detail

        data class ProjectKnowledge(
            val params: ProjectKnowledgeParams? = null,
        ) : Detail
    }

    sealed interface List : ClaudeAppProjectDestination {
        data class ProjectDetails(
            val params: ProjectDetailsScreenParams? = null,
        ) : List
    }
}
