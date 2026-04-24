package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Input for the timer-create tool (v0). */
@Serializable
data class TimerCreateV0Input(
    @SerialName("duration_seconds") val durationSeconds: Int? = null,
    val message: String? = null,
)

/** Input for the user-location tool (v0). */
@Serializable
data class UserLocationV0Input(
    val accuracy: String? = null,
)
