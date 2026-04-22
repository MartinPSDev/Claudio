package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadConfig(
    @SerialName("max_retries") val maxRetries: Int = 3,
    @SerialName("initial_delay_ms") val initialDelayMs: Long = 1_000L,
    @SerialName("delay_multiplier") val delayMultiplier: Double = 2.0,
    @SerialName("max_delay_ms") val maxDelayMs: Long = 30_000L,
    @SerialName("max_file_size_bytes") val maxFileSizeBytes: Long = 100 * 1024 * 1024L,
    @SerialName("direct_filestore_max_bytes") val directFilestoreMaxBytes: Long = 5 * 1024 * 1024L,
) {
    companion object {
        const val FEATURE_KEY = "mobile_upload_config"
        const val DIRECT_FILESTORE_GATE = "claudeai_direct_filestore_uploads"
    }
}
