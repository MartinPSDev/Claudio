package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull

@Serializable
data class GrowthBookFeature(
    val defaultValue: JsonElement,
    val rules: List<GrowthBookRule>? = null
) {
    val on: Boolean
        get() = (defaultValue as? JsonPrimitive)?.booleanOrNull ?: false

    val track: GrowthBookTrack?
        get() = rules?.firstOrNull()?.tracks?.firstOrNull()

    val experiment: GrowthBookExperiment?
        get() = track?.experiment

    val experimentResult: GrowthBookExperimentResult?
        get() = track?.result

    val ruleId: String?
        get() = rules?.firstOrNull()?.id

    val source: String
        get() {
            val firstRule = rules?.firstOrNull()
            if (firstRule?.tracks?.isNotEmpty() == true) return "experiment"
            if (firstRule?.id != null) return "force"
            return "defaultValue"
        }
}
