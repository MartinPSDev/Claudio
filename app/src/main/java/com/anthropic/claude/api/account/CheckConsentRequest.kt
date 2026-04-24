package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to check whether the user has given consent for a specific operation.
 */
@Serializable
data class CheckConsentRequest(
    val consent: JsonElement? = null,
)
