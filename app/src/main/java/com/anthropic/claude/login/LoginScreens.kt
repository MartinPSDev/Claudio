package com.anthropic.claude.login

import kotlinx.serialization.Serializable

/**
 * Sealed class for login flow screens.
 * Subtypes: Welcome, MagicLinkSent, SupervisedUserBlocked
 */
@Serializable
sealed class LoginScreens {

    @Serializable
    data object Welcome : LoginScreens()

    @Serializable
    data class MagicLinkSent(
        val email: String? = null,
        val nonce: String? = null
    ) : LoginScreens()

    @Serializable
    data object SupervisedUserBlocked : LoginScreens()
}

/**
 * Data extracted from a magic link deep link intent.
 */
@Serializable
data class MagicLinkIntentData(
    val nonce: String,
    val encodedEmail: String
)

/**
 * Data extracted from an SSO callback intent.
 */
@Serializable
data class SSOIntentData(
    val code: String,
    val state: String
)
