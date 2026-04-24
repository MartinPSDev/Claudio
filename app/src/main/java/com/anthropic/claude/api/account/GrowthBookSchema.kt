package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * The full GrowthBook evaluation schema for the current user/org.
 * Bundled in the app-start response for client-side feature flag evaluation.
 */
@Serializable
data class GrowthBookSchema(
    val features: JsonElement? = null,
    val user: JsonElement? = null,
    @SerialName("hashing_algorithm") val hashingAlgorithm: String? = null,
)
