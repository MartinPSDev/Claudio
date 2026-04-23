package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The authenticated user's account, returned from the /account endpoint.
 */
@Serializable
data class Account(
    val uuid: String,
    @SerialName("email_address") val emailAddress: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("is_verified") val isVerified: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    val memberships: List<JsonElement>? = null,
    val settings: AccountSettings? = null,
)
