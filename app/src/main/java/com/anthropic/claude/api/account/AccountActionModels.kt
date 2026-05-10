package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request to dismiss a specific banner notification.
 */
@Serializable
data class BannerDismissal(
    @SerialName("banner_id") val bannerId: String,
    @SerialName("dismissed_at") val dismissedAt: String? = null,
)

/**
 * Records the user's acceptance of a legal document (e.g., ToS, privacy policy).
 */
@Serializable
data class DocumentAcceptance(
    @SerialName("document_type") val documentType: String,
    @SerialName("document_version") val documentVersion: String,
    @SerialName("accepted_at") val acceptedAt: String? = null,
)
