package com.anthropic.claude.api.tasks

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to send a message to a running task agent. */
@Serializable
data class SendTaskMessageRequest(val message: JsonElement? = null)
