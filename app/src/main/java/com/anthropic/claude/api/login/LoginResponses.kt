package com.anthropic.claude.api.login

import kotlinx.serialization.Serializable

/**
 * Response after sending a magic link email.
 */
@Serializable
data class SendMagicLinkResponse(
    val sent: Boolean = false,
    val sso_url: String? = null,
    val magic_link_intent_available: Boolean? = null,
    val fallback_code_configuration: CodeConfiguration? = null
)

/**
 * Code configuration for magic link fallback.
 */
@Serializable
data class CodeConfiguration(
    val length: Int? = null,
    val expires_in_seconds: Int? = null
)

/**
 * Response from verifying a magic link or SSO callback.
 */
@Serializable
data class VerifyResponse(
    val success: Boolean = false,
    val account: AccountRef? = null,
    val secret: String? = null,
    val sso_url: String? = null,
    val state: AuthenticationState? = null
)

/**
 * Minimal account reference in verify response.
 */
@Serializable
data class AccountRef(
    val uuid: String? = null,
    val email: String? = null
)

/**
 * Authentication state after verification.
 */
@Serializable
enum class AuthenticationState {
    AUTHENTICATED,
    REQUIRES_PHONE_VERIFICATION,
    REQUIRES_CONSENT,
    UNKNOWN
}
