package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteMessageFlagRequest(
    @SerialName("flag_name")
    val flagName: String
)
