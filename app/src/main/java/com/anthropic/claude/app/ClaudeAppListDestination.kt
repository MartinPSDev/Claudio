package com.anthropic.claude.app

/**
 * Top-level list navigation destinations in the Claude app.
 */
sealed interface ClaudeAppListDestination {
    data object AllProjectsList : ClaudeAppListDestination
    data object ArtifactGallery  : ClaudeAppListDestination
    data object AllChatsList     : ClaudeAppListDestination
    data object CodeRemote       : ClaudeAppListDestination
    data object AgentChat        : ClaudeAppListDestination
    data object Conway           : ClaudeAppListDestination
    data object Orbit            : ClaudeAppListDestination
    data object Tasks            : ClaudeAppListDestination
    data class  Settings(val params: String? = null) : ClaudeAppListDestination
}
