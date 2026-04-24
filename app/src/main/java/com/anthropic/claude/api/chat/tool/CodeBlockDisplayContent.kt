package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Content for displaying a formatted code block in the UI.
 */
@Serializable
data class CodeBlockDisplayContent(
    val language: String? = null,
    val code: String? = null,
    val filename: String? = null,
)
