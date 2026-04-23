package com.anthropic.claude.configs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * GrowthBook sampling rates for feature flags and experiments.
 * Controls what fraction of events are logged to the analytics pipeline.
 */
@Serializable
data class GrowthBookExposureSampleRateConfig(
    @SerialName("feature_sample_rate") val featureSampleRate: Double = 1.0,
    @SerialName("experiment_sample_rate") val experimentSampleRate: Double = 1.0,
    @SerialName("unauthed_feature_sample_rate") val unauthedFeatureSampleRate: Double = 1.0,
    @SerialName("unauthed_experiment_sample_rate") val unauthedExperimentSampleRate: Double = 1.0,
)

/**
 * Locally persisted GrowthBook feature flag overrides (debug/QA use).
 * Stored in SharedPreferences so they survive app restarts.
 */
@Serializable
data class PersistedFeatureOverrides(
    val overrides: Map<String, String> = emptyMap(),
)
