package com.anthropic.claude.app

import com.anthropic.claude.code.remote.CodeRemoteModels
import com.anthropic.claude.project.create.CreateTemplateProjectScreenParams

/**
 * Top-level navigation destinations for the Claude app.
 */
sealed interface ClaudeAppDestination {

    sealed interface Detail : ClaudeAppDestination {
        /** Deep-link to the template project creation flow. */
        data class CreateTemplateProject(
            val params: CreateTemplateProjectScreenParams? = null,
        ) : Detail

        /** Deep-link to a remote code execution session. */
        data class CodeRemoteSession(
            val params: CodeRemoteModels? = null,
        ) : Detail
    }
}
