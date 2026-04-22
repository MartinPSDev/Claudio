package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.Serializable

@Serializable
abstract class MessageFlag {
    abstract val rawValue: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MessageFlag) return false
        return rawValue == other.rawValue
    }

    override fun hashCode(): Int = rawValue.hashCode()

    override fun toString(): String = rawValue

    @Serializable
    data class Known(override val rawValue: String) : MessageFlag()

    @Serializable
    data class Unknown(override val rawValue: String) : MessageFlag()
}
