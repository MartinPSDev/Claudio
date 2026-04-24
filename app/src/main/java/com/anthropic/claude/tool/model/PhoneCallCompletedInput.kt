package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Input signaling completion of a phone-call tool use. */
@Serializable
data class PhoneCallCompletedInput(
    @SerialName("phone_use_tool_id") val phoneUseToolId: String? = null,
)
