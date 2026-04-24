package com.anthropic.claude.api.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to verify Google mobile authentication (Play Integrity). */
@Serializable
data class VerifyGoogleMobileRequest(
    val token: String? = null,
    @SerialName("join_token")             val joinToken: String? = null,
    @SerialName("login_token")            val loginToken: String? = null,
    @SerialName("play_integrity_token")   val playIntegrityToken: String? = null,
    @SerialName("recaptcha_token")        val recaptchaToken: String? = null,
    @SerialName("recaptcha_site_key")     val recaptchaSiteKey: String? = null,
    val source: String? = "claude",
)

/** Request to verify a magic link authentication code. */
@Serializable
data class VerifyMagicLinkRequest(
    val credentials: String? = null,
    @SerialName("play_integrity_token")   val playIntegrityToken: String? = null,
    @SerialName("recaptcha_token")        val recaptchaToken: String? = null,
    @SerialName("recaptcha_site_key")     val recaptchaSiteKey: String? = null,
    val source: String? = "claude",
)
