package com.anthropic.claude.wear

import kotlinx.serialization.Serializable

@Serializable
data class SerializableCookieSlim(
    val name: String,
    val value: String,
)
