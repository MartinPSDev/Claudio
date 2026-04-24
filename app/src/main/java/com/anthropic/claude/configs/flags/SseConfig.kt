package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** SSE connection configuration — controls stream read timeouts. */
@Serializable
data class SseConfig(
    @SerialName("read_timeout_seconds") val readTimeoutSeconds: Int = 120,
)
