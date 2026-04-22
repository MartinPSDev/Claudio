package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamSmoothingConfig(
    @SerialName("smoother_tick_interval_ms") val smootherTickIntervalMs: Int = 16,
    @SerialName("min_markdown_group_size_chars") val minMarkdownGroupSizeChars: Int = 1,
) {
    companion object {
        const val FEATURE_KEY = "android_chat_stream_smoothing"
    }
}
