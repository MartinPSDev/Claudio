package com.anthropic.claude.chat.dialogs

/**
 * Navigation destinations for alert dialogs on the main chat screen.
 */
sealed interface ChatScreenDialog {

    /** Confirm deletion of the current chat. */
    data object Delete : ChatScreenDialog

    /** Confirm stopping an active research session. */
    data object StopResearch : ChatScreenDialog

    /** Dialog dismissed without action. */
    data object Dismissed : ChatScreenDialog
}
