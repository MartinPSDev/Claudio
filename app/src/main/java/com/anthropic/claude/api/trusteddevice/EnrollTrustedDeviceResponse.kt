package com.anthropic.claude.api.trusteddevice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response returned after successfully enrolling a device as trusted.
 * The [deviceToken] is stored locally and sent in subsequent requests as a trust proof.
 */
@Serializable
data class EnrollTrustedDeviceResponse(
    @SerialName("device_id") val deviceId: String,
    @SerialName("device_token") val deviceToken: String? = null,
)
