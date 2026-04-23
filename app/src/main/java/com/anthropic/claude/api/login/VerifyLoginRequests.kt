package com.anthropic.claude.api.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for verifying a Google Sign-In token (mobile flow).
 */
@Serializable
data class VerifyGoogleMobileRequest(
    val token: String,
    val source: String = "claude",
    @SerialName("login_token") val loginToken: String? = null,
    @SerialName("join_token") val joinToken: String? = null,
    @SerialName("play_integrity_token") val playIntegrityToken: String? = null,
    @SerialName("recaptcha_token") val recaptchaToken: String? = null,
    @SerialName("recaptcha_site_key") val recaptchaSiteKey: String? = null,
)

/**
 * Request body for verifying a magic link credential.
 */
@Serializable
data class VerifyMagicLinkRequest(
    val credentials: String,
    val source: String = "claude",
    @SerialName("play_integrity_token") val playIntegrityToken: String? = null,
    @SerialName("recaptcha_token") val recaptchaToken: String? = null,
    @SerialName("recaptcha_site_key") val recaptchaSiteKey: String? = null,
)
