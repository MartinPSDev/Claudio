package com.anthropic.claude.api.errors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Additional error context returned alongside a [ResponseWithError].
 */
@Serializable
data class ErrorDetails(
    @SerialName("error_code") val errorCode: String? = null,
)

/**
 * Standard API error envelope returned by Anthropic endpoints on 4xx/5xx responses.
 */
@Serializable
data class ResponseWithError(
    val type: String? = null,
    @SerialName("error_code") val errorCode: String? = null,
    val message: String? = null,
    val resource: String? = null,
    val details: ErrorDetails? = null,
)

/**
 * Wraps a [ResponseWithError] inside a top-level "error" key.
 * Common pattern for legacy API responses.
 */
@Serializable
data class WrappedResponseWithError(
    val error: ResponseWithError? = null,
)

/**
 * Thrown when the HTTP response body cannot be parsed as the expected JSON structure.
 */
class ResponseJsonParseException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
