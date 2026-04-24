package com.anthropic.claude.api.notification

import kotlinx.serialization.Serializable

/**
 * Request to trigger a test push notification for debugging.
 */
@Serializable
data class TestPushRequest(
    val category: String? = null,
)
