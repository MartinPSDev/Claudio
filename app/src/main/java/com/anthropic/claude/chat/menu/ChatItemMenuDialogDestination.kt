package com.anthropic.claude.chat.menu

/**
 * Navigation destinations for the chat item context menu dialog.
 */
sealed interface ChatItemMenuDialogDestination {

    /** User initiated rename of the chat. */
    data object Rename        : ChatItemMenuDialogDestination

    /** Dialog dismissed without action. */
    data object Dismissed     : ChatItemMenuDialogDestination

    /** Confirm delete conversation. */
    data object Delete        : ChatItemMenuDialogDestination

    /** Move conversation to a different project. */
    data object ChangeProject : ChatItemMenuDialogDestination
}
