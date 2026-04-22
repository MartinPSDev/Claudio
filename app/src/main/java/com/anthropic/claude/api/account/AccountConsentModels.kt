package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Records when a user dismissed a specific in-app banner.
 */
@Serializable
data class BannerDismissal(
    @SerialName("banner_id") val bannerId: String? = null,
    @SerialName("dismissed_at") val dismissedAt: String? = null,
)

/**
 * Records the user's acceptance of a legal document (ToS, privacy policy, etc.).
 */
@Serializable
data class DocumentAcceptance(
    @SerialName("document_id") val documentId: String,
    @SerialName("accepted_via_checkbox") val acceptedViaCheckbox: Boolean? = null,
)
