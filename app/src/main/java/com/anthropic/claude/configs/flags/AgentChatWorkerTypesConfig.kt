package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentChatWorkerTypesConfig(
    @SerialName("worker_types") val workerTypes: List<String>? = null,
) {
    companion object {
        const val FEATURE_KEY = "android_agent_chat_worker_types"
    }
}
