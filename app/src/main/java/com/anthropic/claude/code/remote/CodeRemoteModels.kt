package com.anthropic.claude.code.remote

/**
 * Screen navigation parameters for the remote code editor session.
 * Extracted (13KB).
 */
data class CodeRemoteSessionScreenParams(
    val sessionId: String? = null,
    val isRootScreen: Boolean = false,
) {
    override fun toString(): String =
        "CodeRemoteSessionScreenParams(sessionId=$sessionId, isRootScreen=$isRootScreen)"
}
