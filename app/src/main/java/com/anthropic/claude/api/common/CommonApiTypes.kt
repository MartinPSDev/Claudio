package com.anthropic.claude.api.common

import kotlinx.serialization.Serializable

/**
 * Cloudflare waiting room response when rate limited.
 */
@Serializable
data class CloudflareWaitingRoomResponse(
    val in_waiting_room: Boolean = false,
    val estimated_wait_seconds: Int? = null,
    val position: Int? = null
)

/**
 * Empty response with success flag.
 */
@Serializable
data class EmptyResponseWithSuccess(
    val success: Boolean = true
)

/**
 * Pixel size for image dimensions.
 */
@Serializable
data class PixelSize(
    val width: Int = 0,
    val height: Int = 0
)
