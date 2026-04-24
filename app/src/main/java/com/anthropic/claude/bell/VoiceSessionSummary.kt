package com.anthropic.claude.bell

/**
 * Summary data for a completed voice session — emitted at session end for analytics.
 */
data class VoiceSessionSummary(
    val organizationId: String? = null,
    val voiceSessionId: String? = null,
    val conversationId: String? = null,
    val numTurns: Int = 0,
    val sessionDurationMs: Long = 0L,
)
