package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Status of a feature for a given user.
 */
@Serializable
enum class FeatureAccessStatus {
    @SerialName("unknown")                  UNKNOWN,
    @SerialName("available")                AVAILABLE,
    @SerialName("blocked_by_entitlement")   BLOCKED_BY_ENTITLEMENT,
    @SerialName("blocked_by_org_admin")     BLOCKED_BY_ORG_ADMIN,
    @SerialName("blocked_by_platform")      BLOCKED_BY_PLATFORM,
}
