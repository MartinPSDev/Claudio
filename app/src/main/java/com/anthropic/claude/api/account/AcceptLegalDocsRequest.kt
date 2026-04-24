package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to accept one or more legal documents (Terms of Service, Privacy Policy, etc.).
 */
@Serializable
data class AcceptLegalDocsRequest(
    val acceptances: List<JsonElement>? = null,
)
