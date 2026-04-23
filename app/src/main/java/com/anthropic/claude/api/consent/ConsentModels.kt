package com.anthropic.claude.api.consent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Categories of legal/health consent managed by the consent API.
 */
@Serializable
enum class ConsentType {
    @SerialName("consumer_health") CONSUMER_HEALTH,
}

/**
 * The type of external entity (tool, extension, server) for which consent is tracked.
 */
@Serializable
enum class EntityType {
    @SerialName("mcp_remote_server") MCP_REMOTE_SERVER,
    @SerialName("desktop_extension") DESKTOP_EXTENSION,
    @SerialName("dxt_version") DXT_VERSION,
    @SerialName("local_tool") LOCAL_TOOL,
}

/**
 * Response indicating whether the user has given consent for a specific [ConsentType].
 */
@Serializable
data class HasConsentResponse(
    @SerialName("has_consent") val hasConsent: Boolean,
)

/**
 * Full user consent record returned from the consent query endpoint.
 */
@Serializable
data class UserConsentResponse(
    @SerialName("has_consent") val hasConsent: Boolean = false,
    @SerialName("consent_type") val consentType: ConsentType? = null,
    @SerialName("entity_type") val entityType: EntityType? = null,
    @SerialName("entity_id") val entityId: String? = null,
    @SerialName("consented_at") val consentedAt: String? = null,
)
