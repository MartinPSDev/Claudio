package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable

/**
 * Response from an experience action.
 */
@Serializable
data class ExperienceActionResponse(
    val success: Boolean = false,
    val error: String? = null
)

/**
 * Asset for an experience (image, video, etc).
 */
@Serializable
data class ExperienceAsset(
    val url: String? = null,
    val alt_text: String? = null,
    val media_type: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val resize_mode: ExperienceAssetResizeMode? = null,
    val image_variants: ExperienceAssetImageVariants? = null
)

/**
 * Image variants for an experience asset (multi-resolution).
 */
@Serializable
data class ExperienceAssetImageVariants(
    val small: ExperienceAssetScaledImage? = null,
    val medium: ExperienceAssetScaledImage? = null,
    val large: ExperienceAssetScaledImage? = null
)

/**
 * Scaled image for a specific resolution.
 */
@Serializable
data class ExperienceAssetScaledImage(
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null
)

/**
 * Resize mode for experience assets.
 */
@Serializable
enum class ExperienceAssetResizeMode {
    CONTAIN,
    COVER,
    STRETCH,
    CENTER
}

/**
 * Bullet point in an experience.
 */
@Serializable
data class ExperienceBullet(
    val text: String? = null,
    val icon: String? = null
)

/**
 * Button in an experience.
 */
@Serializable
data class ExperienceButton(
    val text: String? = null,
    val type: ExperienceButtonType? = null,
    val action: String? = null,
    val url: String? = null
)

/**
 * Button type.
 */
@Serializable
enum class ExperienceButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY,
    DESTRUCTIVE
}

/**
 * Cooldown configuration for experience re-display.
 */
@Serializable
data class ExperienceCooldown(
    val duration_hours: Int? = null,
    val max_impressions: Int? = null
)

/**
 * Where the experience is placed.
 */
@Serializable
enum class ExperiencePlacement {
    CHAT_LIST,
    CHAT,
    SETTINGS,
    ONBOARDING,
    MODAL,
    TOOLTIP
}

/**
 * Tooltip action type.
 */
@Serializable
enum class ExperienceTooltipActionType {
    DISMISS,
    NAVIGATE,
    OPEN_URL
}

/**
 * Tooltip location.
 */
@Serializable
enum class ExperienceTooltipLocation {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT
}

/**
 * Track action type for analytics.
 */
@Serializable
enum class ExperienceTrackActionType {
    SHOWN,
    ACTIONED,
    DISMISSED,
    UNKNOWN
}

/**
 * Cache type for experience data.
 */
@Serializable
enum class CacheType {
    MEMORY,
    DISK,
    NONE
}

/**
 * Unknown client action fallback.
 */
@Serializable
data class UnknownClientAction(
    val type: String? = null,
    val raw: String? = null
)

/**
 * Unknown track action data fallback.
 */
@Serializable
data class TrackUnknownActionData(
    val type: String? = null,
    val raw: String? = null
)
