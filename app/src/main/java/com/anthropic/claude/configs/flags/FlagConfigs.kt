package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A single localizable tool search mode option displayed in the UI.
 */
@Serializable
data class ToolSearchModeConfig(
    val title: String? = null,
    val description: String? = null,
    @SerialName("title_key") val titleKey: String? = null,
    @SerialName("description_key") val descriptionKey: String? = null,
    val value: String? = null,
)

/**
 * A single onboarding page returned from the server.
 */
@Serializable
data class OnboardingPage(
    val id: String? = null,
    val title: String? = null,
    val body: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
)

/**
 * Server-driven onboarding configuration — list of pages to display.
 */
@Serializable
data class OnboardingConfig(
    val pages: List<OnboardingPage> = emptyList(),
)

/**
 * Defines which agent worker types are available for a given session.
 */
@Serializable
data class AgentChatWorkerTypesConfig(
    @SerialName("worker_types") val workerTypes: List<String> = emptyList(),
)

/**
 * File upload limits enforced on the client side.
 * Mirrors server-side limits to give instant user feedback.
 */
@Serializable
data class FileUploadConfig(
    @SerialName("max_in_context_file_bytes") val maxInContextFileBytes: Long = 10_485_760L,
    @SerialName("max_inline_paste_bytes") val maxInlinePasteBytes: Long = 1_048_576L,
)

/**
 * Upload retry + chunking parameters for the file upload pipeline.
 * From UploadConfig.smali (24 KB).
 */
@Serializable
data class UploadConfig(
    @SerialName("max_retries") val maxRetries: Int = 3,
    @SerialName("initial_delay_ms") val initialDelayMs: Long = 1000L,
    @SerialName("delay_multiplier") val delayMultiplier: Double = 2.0,
    @SerialName("max_delay_ms") val maxDelayMs: Long = 30_000L,
    @SerialName("max_file_size_bytes") val maxFileSizeBytes: Long = 52_428_800L,
    @SerialName("direct_filestore_max_bytes") val directFilestoreMaxBytes: Long = 5_242_880L,
)

/**
 * Token streaming smoothing parameters for the chat renderer.
 */
@Serializable
data class StreamSmoothingConfig(
    @SerialName("smoother_tick_interval_ms") val smootherTickIntervalMs: Long = 50L,
    @SerialName("min_markdown_group_size_chars") val minMarkdownGroupSizeChars: Int = 10,
)

/**
 * Agent chat onboarding config — desktop download URL for cross-device promotion.
 */
@Serializable
data class AgentChatOnboardingConfig(
    @SerialName("desktop_download_url") val desktopDownloadUrl: String? = null,
)

/**
 * Help center URL for the image search feature.
 */
@Serializable
data class ImageSearchHelpCenterConfig(
    val url: String? = null,
)
