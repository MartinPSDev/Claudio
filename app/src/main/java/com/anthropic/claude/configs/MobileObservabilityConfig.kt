package com.anthropic.claude.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Server-provided observability sampling rates for mobile telemetry.
 */
@Serializable
data class MobileObservabilityConfig(
    @SerialName("network_request_sample_rate") val networkRequestSampleRate: Float = 1.0f,
    @SerialName("datadog_request_trace_sample_rate") val datadogRequestTraceSampleRate: Float = 1.0f,
    @SerialName("datadog_rum_profiler_sample_rate") val datadogRumProfilerSampleRate: Float = 1.0f,
    @SerialName("streaming_jank_sample_rate") val streamingJankSampleRate: Float = 1.0f,
)
