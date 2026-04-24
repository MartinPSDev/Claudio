package com.anthropic.claude.login

/**
 * Data extracted from the magic link deep link intent.
 */
data class MagicLinkIntentData(
    val nonce: String? = null,
    val encodedEmail: String? = null,
)

/**
 * Data extracted from the SSO callback deep link intent.
 */
data class SSOIntentData(
    val code: String? = null,
    val state: String? = null,
)
