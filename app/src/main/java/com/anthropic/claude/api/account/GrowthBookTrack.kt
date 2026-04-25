package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable

@Serializable
data class GrowthBookTrack(
    val experiment: GrowthBookExperiment,
    val result: GrowthBookExperimentResult
)
