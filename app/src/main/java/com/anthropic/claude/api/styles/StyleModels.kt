package com.anthropic.claude.api.styles

import kotlinx.serialization.Serializable

/**
 * A writing-style attribute describing one dimension of Claude's response style.
 * [name] is the attribute label (e.g. "formal", "concise").
 * [percentage] is a 0.0–1.0 weight for that attribute.
 */
@Serializable
data class StyleAttribute(
    val name: String,
    val percentage: Double? = null,
)

/**
 * A user-defined writing style that Claude does not recognise natively.
 * Contains a list of weighted [StyleAttribute] entries.
 */
@Serializable
data class UnknownStyle(
    val attributes: List<StyleAttribute> = emptyList(),
    val description: String? = null,
)
