package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GrowthBookRule(
    val force: JsonElement? = null,
    val tracks: List<GrowthBookTrack>? = null,
    val id: String? = null
)
