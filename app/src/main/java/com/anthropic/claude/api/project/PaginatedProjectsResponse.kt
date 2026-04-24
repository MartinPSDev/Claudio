package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Paginated response for a projects list query.
 */
@Serializable
data class PaginatedProjectsResponse(
    val data: List<JsonElement>? = null,
    val pagination: JsonElement? = null,
)
