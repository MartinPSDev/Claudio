package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountProfile(
    val uuid: String,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("display_email") val displayEmail: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("is_claude_ai_user") val isClaudeAiUser: Boolean = false,
)
