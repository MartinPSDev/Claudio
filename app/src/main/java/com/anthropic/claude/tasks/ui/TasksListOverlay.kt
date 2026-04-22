package com.anthropic.claude.tasks.ui

import kotlinx.serialization.Serializable

sealed interface TasksListOverlay {

    @Serializable
    data object None : TasksListOverlay

    @Serializable
    data object BrowserTakeover : TasksListOverlay
}
