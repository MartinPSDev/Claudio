package com.anthropic.claude.api.consent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body to check the current consent status for a given entity.
 */
@Serializable
data class CheckConsentRequest(
    @SerialName("consent_type") val consentType: String,
    @SerialName("entity_id") val entityId: String? = null,
    @SerialName("entity_type") val entityType: String? = null,
    @SerialName("consent_hash") val consentHash: String? = null,
)

/**
 * Request body to revoke a previously granted consent.
 */
@Serializable
data class RevokeConsentRequest(
    @SerialName("consent_uuid") val consentUuid: String,
    @SerialName("consent_type") val consentType: String? = null,
    @SerialName("entity_id") val entityId: String? = null,
    @SerialName("entity_type") val entityType: String? = null,
)
