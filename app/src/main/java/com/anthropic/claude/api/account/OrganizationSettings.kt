package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Organization-level settings controlling privacy and invite policies.
 */
@Serializable
data class OrganizationSettings(
    @SerialName("claude_console_privacy") val claudeConsolePrivacy: String? = null,
    @SerialName("allowed_invite_domains") val allowedInviteDomains: List<String>? = null,
)
