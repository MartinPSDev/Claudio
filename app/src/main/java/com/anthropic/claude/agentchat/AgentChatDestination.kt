package com.anthropic.claude.agentchat

import kotlinx.serialization.Serializable

sealed interface AgentChatDestination {

    @Serializable
    data object Home : AgentChatDestination
}
