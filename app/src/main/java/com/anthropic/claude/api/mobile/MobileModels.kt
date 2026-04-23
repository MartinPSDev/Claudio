package com.anthropic.claude.api.mobile

import kotlinx.serialization.Serializable

/**
 * A single localized string value keyed by a translation key.
 */
@Serializable
data class LocalizedString(
    val value: String,
)

/**
 * Request to fetch translations for a given locale (e.g. "en-US", "es-ES").
 */
@Serializable
data class GetTranslationsRequest(
    val locale: String,
)

/**
 * Response containing a map of translation key → [LocalizedString].
 */
@Serializable
data class GetTranslationsResponse(
    val translations: Map<String, LocalizedString>? = null,
)
