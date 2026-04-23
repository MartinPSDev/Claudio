package com.anthropic.claude.api.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for initiating the magic link login flow.
 */
@Serializable
data class SendMagicLinkRequest(
    @SerialName("email_address") val emailAddress: String,
    val client: String = "claude",
    val source: String = "android",
    @SerialName("login_intent") val loginIntent: String? = null,
    @SerialName("utc_offset") val utcOffset: Int? = null,
    @SerialName("recaptcha_token") val recaptchaToken: String? = null,
    @SerialName("recaptcha_site_key") val recaptchaSiteKey: String? = null,
)
