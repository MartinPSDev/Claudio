package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configuration for the speech input (STT) feature.
 */
@Serializable
data class SpeechInputConfig(
    @SerialName("maximum_request_duration_seconds") val maximumRequestDurationSeconds: Int? = null,
    @SerialName("default_language_code") val defaultLanguageCode: String? = null,
    @SerialName("supported_languages") val supportedLanguages: List<String>? = null,
)
