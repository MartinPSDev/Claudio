package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Pagination metadata for list responses.
 */
@Serializable
data class Pagination(
    @SerialName("has_more") val hasMore: Boolean = false,
    val total: Int? = null,
    val limit: Int? = null,
    val offset: Int? = null,
)
