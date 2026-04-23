package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request body for updating the current user's account profile.
 */
@Serializable
data class UpdateAccountRequest(
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("age_is_verified") val ageIsVerified: Boolean? = null,
    @SerialName("accept_document_ids") val acceptDocumentIds: List<String>? = null,
    val settings: JsonElement? = null,
)

/**
 * Response from the app-start endpoint, bundling account + org state.
 */
@Serializable
data class AppStartResponse(
    val account: JsonElement? = null,
    @SerialName("current_user_access") val currentUserAccess: JsonElement? = null,
    @SerialName("org_growthbook") val orgGrowthbook: JsonElement? = null,
    @SerialName("server_localizations") val serverLocalizations: JsonElement? = null,
)
