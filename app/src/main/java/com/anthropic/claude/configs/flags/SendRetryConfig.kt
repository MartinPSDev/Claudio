package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Retry configuration for network requests. */
@Serializable
data class SendRetryConfig(
    @SerialName("max_attempts")          val maxAttempts: Int? = null,
    @SerialName("initial_delay_ms")      val initialDelayMs: Long? = null,
    @SerialName("max_delay_ms")          val maxDelayMs: Long? = null,
    @SerialName("delay_multiplier")      val delayMultiplier: Double? = null,
    @SerialName("retry_on_http_codes")   val retryOnHttpCodes: List<Int>? = null,
    @SerialName("retry_on_io_exception") val retryOnIoException: Boolean? = null,
)
