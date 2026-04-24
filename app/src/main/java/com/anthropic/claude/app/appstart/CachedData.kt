package com.anthropic.claude.app.appstart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A locally cached app-start response with its cache timestamp.
 * Used to avoid a network roundtrip on cold start when data is fresh.
 */
@Serializable
data class CachedData(
    val response: JsonElement? = null,
    @SerialName("cachedAtMillis") val cachedAtMillis: Long = 0L,
)
