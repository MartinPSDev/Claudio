package com.anthropic.claude.settings.internal

/**
 * Navigation destinations for the internal debug settings app.
 * Only available in debug/internal builds.
 */
sealed interface InternalSettingsAppScreen {

    /** Override GrowthBook feature flags with custom JSON values. */
    data class GrowthBookFeatureJsonEditor(
        val featureId: String,
    ) : InternalSettingsAppScreen

    /** View and toggle all GrowthBook feature flag overrides. */
    data object GrowthBookOverrideScreen : InternalSettingsAppScreen

    /** Select the API endpoint (prod / staging / local). */
    data object EndpointSelectionScreen : InternalSettingsAppScreen

    /** Simulate network conditions (latency, errors). */
    data object NetworkSimulationScreen : InternalSettingsAppScreen
}
