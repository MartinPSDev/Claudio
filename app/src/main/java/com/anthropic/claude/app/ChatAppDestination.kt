package com.anthropic.claude.app

import com.anthropic.claude.chat.input.draft.DraftModels

/**
 * Deep-link destination to open a specific chat conversation.
 */
data class ChatAppDestination(
    val params: DraftModels? = null,
)
