package com.anthropic.claude.ui.code

/**
 * Tracks selected answers and navigation state for a multi-question
 * AskUser interaction within the remote code session.
 * Fields from PendingAskUserQuestionSelections.smali (25KB).
 */
data class PendingAskUserQuestionSelections(
    val toolUseId: String,
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, String> = emptyMap(),
    val otherTexts: Map<Int, String> = emptyMap(),
) {
    override fun toString(): String =
        "PendingAskUserQuestionSelections(toolUseId=$toolUseId, currentQuestionIndex=$currentQuestionIndex)"
}

/**
 * A comment on a specific line in a code diff view.
 * Fields from DiffLineComment.smali (28KB).
 */
data class DiffLineComment(
    val id: String,
    val filePath: String? = null,
    val lineKey: String? = null,
    val lineContent: String? = null,
    val oldLineNumber: Int? = null,
    val newLineNumber: Int? = null,
    val commentText: String? = null,
    val createdAt: Long? = null,
) {
    override fun toString(): String =
        "DiffLineComment(id=$id, filePath=$filePath, newLineNumber=$newLineNumber)"
}

/**
 * Complete input state for a remote code editor session.
 * Persisted locally to survive app backgrounding.
 * Fields from SessionInputData.smali (34KB).
 */
data class SessionInputData(
    val sessionId: String,
    val inputText: String = "",
    val lastModified: Long? = null,
    val comments: List<DiffLineComment> = emptyList(),
    val pendingAskUserQuestionSelections: PendingAskUserQuestionSelections? = null,
    val submittedAskUserQuestionAnswers: Map<String, String> = emptyMap(),
) {
    override fun toString(): String =
        "SessionInputData(sessionId=$sessionId, lastModified=$lastModified)"
}
