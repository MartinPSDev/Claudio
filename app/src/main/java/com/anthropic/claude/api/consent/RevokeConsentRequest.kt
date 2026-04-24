package com.anthropic.claude.api.consent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to revoke a previously granted consent. */
@Serializable
data class RevokeConsentRequest(
    @SerialName("consent_uuid")  val consentUuid: String? = null,
    @SerialName("consent_type")  val consentType: String? = null,
    @SerialName("entity_id")     val entityId: String? = null,
    @SerialName("entity_type")   val entityType: String? = null,
)
