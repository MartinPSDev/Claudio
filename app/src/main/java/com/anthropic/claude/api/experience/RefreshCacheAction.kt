package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Server-driven action that instructs the client to refresh its experience cache. */
@Serializable
data class RefreshCacheAction(
    val cache: JsonElement? = null,
)
