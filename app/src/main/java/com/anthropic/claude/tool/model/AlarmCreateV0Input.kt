package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Input for the alarm-create tool (v0).
 */
@Serializable
data class AlarmCreateV0Input(
    val days: List<String>? = null,
    val hour: Int? = null,
    val minute: Int? = null,
    val message: String? = null,
    val vibrate: Boolean? = null,
)
