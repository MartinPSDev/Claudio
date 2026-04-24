package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to update an account's profile and settings. */
@Serializable
data class UpdateAccountRequest(
    @SerialName("full_name")           val fullName: String? = null,
    @SerialName("display_name")        val displayName: String? = null,
    @SerialName("accept_document_ids") val acceptDocumentIds: List<String>? = null,
    @SerialName("age_is_verified")     val ageIsVerified: Boolean? = null,
    val settings: JsonElement? = null,
)
