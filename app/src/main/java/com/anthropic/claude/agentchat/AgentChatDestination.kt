package com.anthropic.claude.agentchat

import kotlinx.serialization.Serializable

/**
 * Navigation destinations within the Agent Chat (Computer Use) flow.
 */
sealed interface AgentChatDestination {

    /** Main home screen of the Agent Chat module. */
    @Serializable
    data object Home : AgentChatDestination
}
