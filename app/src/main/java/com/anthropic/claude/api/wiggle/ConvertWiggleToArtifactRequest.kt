package com.anthropic.claude.api.wiggle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Conversion operations for promoting a Wiggle file to a Claude artifact.
 */
@Serializable
enum class WiggleArtifactConversionOperation {
    @SerialName("publish") PUBLISH,
    @SerialName("share")   SHARE,
}

/**
 * Request to convert a Wiggle file into a publishable/shareable artifact.
 */
@Serializable
data class ConvertWiggleToArtifactRequest(
    val path: String? = null,
    val operation: WiggleArtifactConversionOperation? = null,
)
