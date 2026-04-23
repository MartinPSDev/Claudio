package com.anthropic.claude.chat.bottomsheet.model

/**
 * Abstract base for chat bottom sheet state models.
 * Represents the content/state contract for all chat-related bottom sheets
 */
abstract class ChatBottomSheetModel

/**
 * A bottom sheet option item displayed in the chat options menu
 */
data class ChatBottomSheetOption(
    val id: String,
    val label: String,
    val iconRes: Int? = null,
    val isDestructive: Boolean = false,
    val isEnabled: Boolean = true,
)
