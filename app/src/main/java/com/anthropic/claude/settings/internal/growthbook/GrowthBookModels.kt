package com.anthropic.claude.settings.internal.growthbook

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The data type of a GrowthBook feature flag value.
 * Exact enum values extracted bytecode.
 */
@Serializable
enum class GateKind {
    @SerialName("BOOLEAN") BOOLEAN,
    @SerialName("STRING") STRING,
    @SerialName("NUMBER") NUMBER,
    @SerialName("JSON") JSON,
    @SerialName("UNKNOWN") UNKNOWN,
}

/**
 * A single GrowthBook feature override with its value and type.
 */
@Serializable
data class GrowthBookFeatureOverride(
    val key: String,
    val value: String,
    val kind: GateKind = GateKind.UNKNOWN,
)

/**
 * Persisted custom feature flag overrides set via the internal debug screen.
 * Used by QA/engineers to override GrowthBook flags without a server change.
 */
@Serializable
data class PersistedCustomFeatures(
    val features: Map<String, GrowthBookFeatureOverride> = emptyMap(),
)

/**
 * Screen data model for the GrowthBook override debug screen.
 * Allows internal users to inspect and override feature flag values.
 */
data class GrowthBookOverrideScreenData(
    val allFeatures: Map<String, GateKind> = emptyMap(),
    val customFeatures: PersistedCustomFeatures = PersistedCustomFeatures(),
) {
    companion object {
        val Empty = GrowthBookOverrideScreenData()
    }
}
