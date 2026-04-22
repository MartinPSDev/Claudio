package com.anthropic.claude.tasks.ui

import kotlinx.serialization.Serializable

sealed interface TasksBottomSheetDestination {

    @Serializable
    data object Closed : TasksBottomSheetDestination

    @Serializable
    data object TaskList : TasksBottomSheetDestination

    @Serializable
    data class TaskDetail(val taskId: String) : TasksBottomSheetDestination

    @Serializable
    data class EventInspector(val eventId: String) : TasksBottomSheetDestination

    @Serializable
    data class FormInput(val formId: String) : TasksBottomSheetDestination
}
