package com.anthropic.claude.conway

import kotlinx.serialization.json.JsonObject

sealed interface ConwayAppScreen {
    data class Chat(
        val sessionKey: String,
        val headers: Map<String, String>,
        val pendingMessages: List<JsonObject>,
        val orgPath: String,
    ) : ConwayAppScreen

    data class System(
        val action: String,
    ) : ConwayAppScreen
}
