package com.anthropic.claude.api.chat.messages

import com.anthropic.claude.types.strings.MessageId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletionMessage(
    val uuid: MessageId? = null,
    @SerialName("parent_uuid") val parentUuid: MessageId? = null,
)
