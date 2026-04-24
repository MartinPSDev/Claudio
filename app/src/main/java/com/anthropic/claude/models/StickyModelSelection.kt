package com.anthropic.claude.models

/**
 * Persists the user's last explicitly selected model so it survives
 * across navigation and restarts.
 */
data class StickyModelSelection(
    val modelId: String,
    val isOverflow: Boolean = false,
    val selectionTimestampMillis: Long = 0L,
)
