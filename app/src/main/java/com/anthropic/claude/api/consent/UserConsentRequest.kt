package com.anthropic.claude.api.consent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for granting user consent for a given document or entity.
 */
@Serializable
data class UserConsentRequest(
    @SerialName("consent_type") val consentType: String,
    @SerialName("entity_id") val entityId: String? = null,
    @SerialName("entity_type") val entityType: String? = null,
    @SerialName("consent_hash") val consentHash: String? = null,
)
