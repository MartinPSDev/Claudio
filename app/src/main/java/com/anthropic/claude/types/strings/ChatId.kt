package com.anthropic.claude.types.strings

import kotlinx.serialization.Serializable

/** Inline value class wrapping a typed chat/conversation identifier. */
@Serializable
@JvmInline
value class ChatId(val value: String)
