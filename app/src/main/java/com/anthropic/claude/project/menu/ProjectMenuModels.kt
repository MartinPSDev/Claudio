package com.anthropic.claude.project.menu

/**
 * Navigation destinations for the project item context menu dialog.
 * Each subtype represents an action the user can take on a project item.
 * Extracted from ProjectItemMenuDialogDestination smali files.
 */
sealed interface ProjectItemMenuDialogDestination {

    /** User confirmed deletion of the project item. */
    data object Delete : ProjectItemMenuDialogDestination

    /** User initiated rename of the project item. */
    data object Rename : ProjectItemMenuDialogDestination

    /** Dialog was dismissed without an action. */
    data object Dismissed : ProjectItemMenuDialogDestination
}
