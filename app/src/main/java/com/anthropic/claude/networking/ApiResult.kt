package com.anthropic.claude.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A sealed result wrapper for all API calls.
 * Avoids throwing exceptions across coroutine boundaries.
 */
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(
        val code: Int,
        val message: String? = null,
        val body: JsonElement? = null,
    ) : ApiResult<Nothing>
    data object NetworkError : ApiResult<Nothing>
}

/**
 * Structured error body returned by the Claude API on 4xx/5xx responses.
 */
@Serializable
data class ClaudeApiError(
    val type: String? = null,
    val error: ClaudeApiErrorDetail? = null,
)

@Serializable
data class ClaudeApiErrorDetail(
    val type: String? = null,
    val message: String? = null,
    @SerialName("status_code") val statusCode: Int? = null,
)
