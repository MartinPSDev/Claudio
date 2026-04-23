package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from the batch analytics event logging endpoint.
 * Reports how many events were accepted or rejected by the server.
 */
@Serializable
data class BatchEventLoggingResponse(
    @SerialName("accepted_count") val acceptedCount: Int = 0,
    @SerialName("rejected_count") val rejectedCount: Int = 0,
)
