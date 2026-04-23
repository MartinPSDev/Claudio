package com.anthropic.claude.api.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Saved state before the OAuth redirect, used to restore context on callback.
 * [origin] identifies the screen/flow that triggered the auth.
 */
@Serializable
data class PreAuthState(
    val origin: String? = null,
)

/**
 * Response from the start-auth endpoint — contains the OAuth redirect URL.
 */
@Serializable
data class StartAuthResponse(
    @SerialName("redirect_url") val redirectUrl: String? = null,
)
