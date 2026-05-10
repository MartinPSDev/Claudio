package com.anthropic.claude.ui.code

import kotlinx.serialization.Serializable

/**
 * A comment on a specific line within a code diff view.
 */
@Serializable
data class DiffLineComment(
    val lineNumber: Int,
    val content: String,
    val author: String? = null,
    val timestamp: Long? = null,
    val isResolved: Boolean = false,
)

/**
 * Tracks the user's selections when Claude asks for input
 * during an agent/code session.
 */
data class PendingAskUserQuestionSelections(
    val questionId: String,
    val selectedOptionIndices: List<Int> = emptyList(),
    val freeTextResponse: String? = null,
    val isSubmitted: Boolean = false,
)

/**
 * Input data for a code editing session (Code Remote / Agent Chat).
 */
data class SessionInputData(
    val sessionId: String,
    val userMessage: String = "",
    val attachedFiles: List<String> = emptyList(),
    val selectedRepository: String? = null,
    val selectedBranch: String? = null,
    val workingDirectory: String? = null,
)
