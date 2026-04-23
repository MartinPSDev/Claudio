package com.anthropic.claude.networking.cookies.serializer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A serializable representation of an HTTP cookie for persistent storage.
 */
@Serializable
data class SerializableCookie(
    val name: String,
    val value: String,
    val domain: String? = null,
    val path: String? = null,
    @SerialName("expiresAt") val expiresAt: Long? = null,
    val secure: Boolean = false,
    @SerialName("httpOnly") val httpOnly: Boolean = false,
    @SerialName("hostOnly") val hostOnly: Boolean = false,
)
