package com.anthropic.claude.api.kyc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Know-Your-Customer identity verification lifecycle states.
 * [isBlocking] returns true for states that prevent the user from proceeding.
 */
@Serializable
enum class KycStatus {
    @SerialName("not_required") NOT_REQUIRED,
    @SerialName("verified") VERIFIED,
    @SerialName("created") CREATED,
    @SerialName("in_progress") IN_PROGRESS,
    @SerialName("manual_review") MANUAL_REVIEW,
    @SerialName("failed") FAILED,
    @SerialName("expired") EXPIRED,
    @SerialName("denied") DENIED,
    @SerialName("unknown") UNKNOWN;

    val isBlocking: Boolean
        get() = this == CREATED || this == IN_PROGRESS || this == MANUAL_REVIEW ||
                this == FAILED || this == EXPIRED || this == DENIED
}

/**
 * Full KYC status response including vendor inquiry details and polling flag.
 */
@Serializable
data class KycStatusResponse(
    val status: KycStatus,
    @SerialName("vendor_inquiry_id") val vendorInquiryId: String? = null,
    @SerialName("vendor_inquiry_url") val vendorInquiryUrl: String? = null,
    @SerialName("should_poll") val shouldPoll: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
)
