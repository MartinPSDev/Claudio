package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompactionStatus(
    val status: Status,
    val message: String? = null,
) {
    @Serializable
    enum class Status {
        @SerialName("compacting") COMPACTING,
        @SerialName("complete") COMPLETE,
        @SerialName("failed") FAILED,
    }
}
