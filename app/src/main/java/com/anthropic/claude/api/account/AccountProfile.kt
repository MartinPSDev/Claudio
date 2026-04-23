package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * User-level conversation preferences stored on the server.
 * Serialized as a JSON string in the account profile response.
 */
@Serializable
data class AccountProfile(
    @SerialName("conversation_preferences") val conversationPreferences: String? = null,
)
