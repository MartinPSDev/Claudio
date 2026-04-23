package com.anthropic.claude.api.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * User's notification preferences per feature category.
 * Each field maps to an opt-in/opt-out toggle in the notification settings screen.
 */
@Serializable
data class FeaturePreference(
    val compass: Boolean? = null,
    val completion: Boolean? = null,
    val dispatch: Boolean? = null,
    val marketing: Boolean? = null,
    val bogosort: Boolean? = null,
)
