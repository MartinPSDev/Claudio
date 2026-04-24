package com.anthropic.claude.api.purchase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Google Play purchase receipt submitted for server-side verification.
 */
@Serializable
data class PurchaseReceipt(
    @SerialName("purchase_token") val purchaseToken: String? = null,
    @SerialName("organization_id") val organizationId: String? = null,
)
