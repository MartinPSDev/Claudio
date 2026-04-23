package com.anthropic.claude.api.chat

import kotlinx.serialization.Serializable

/**
 * A text field that may contain sensitive data (e.g. API keys, passwords).
 * [value] holds the actual content; [ref] is an opaque server-side reference
 * used to retrieve or validate the sensitive value without exposing it.
 */
@Serializable
data class SensitiveTextField(
    val value: String,
    val ref: String,
)

/**
 * Response from the fill-sensitive-text endpoint that resolves a [ref] to its [value].
 */
@Serializable
data class FillSensitiveTextResponse(
    val value: String? = null,
)
