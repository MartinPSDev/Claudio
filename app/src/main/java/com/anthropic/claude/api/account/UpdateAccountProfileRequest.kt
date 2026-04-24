package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to update the user's account profile and conversation preferences. */
@Serializable
data class UpdateAccountProfileRequest(
    @SerialName("conversation_preferences") val conversationPreferences: JsonElement? = null,
)
