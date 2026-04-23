package com.anthropic.claude.api.purchase

import com.anthropic.claude.api.kyc.KycStatusResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the in-app purchase verification endpoint.
 * If [success] is false, [idvRequirement] contains the KYC step required before purchase is allowed.
 */
@Serializable
data class VerifyPurchaseResponse(
    val success: Boolean = false,
    @SerialName("idv_requirement") val idvRequirement: KycStatusResponse? = null,
)
