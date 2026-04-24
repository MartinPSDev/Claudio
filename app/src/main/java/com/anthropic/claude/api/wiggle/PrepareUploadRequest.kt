package com.anthropic.claude.api.wiggle

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Request to prepare an upload slot for one or more files via the Wiggle API.
 */
@Serializable
data class PrepareUploadRequest(
    val files: List<JsonElement>? = null,
)
