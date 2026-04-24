package com.anthropic.claude.api.trusteddevice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to enroll the current device as a trusted device. */
@Serializable
data class EnrollTrustedDeviceRequest(
    @SerialName("display_name") val displayName: String? = null,
)
