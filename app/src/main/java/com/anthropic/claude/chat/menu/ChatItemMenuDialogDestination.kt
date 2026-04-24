package com.anthropic.claude.chat.menu

/**
 * Navigation destinations for the chat item context menu dialog.
 */
sealed interface ChatItemMenuDialogDestination {

    /** User initiated rename of the chat. */
    data object Rename : ChatItemMenuDialogDestination

    /** Dialog dismissed without action. */
    data object Dismissed : ChatItemMenuDialogDestination
}
