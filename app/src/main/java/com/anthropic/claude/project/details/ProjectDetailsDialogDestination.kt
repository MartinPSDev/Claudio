package com.anthropic.claude.project.details

import kotlinx.serialization.Serializable

sealed interface ProjectDetailsDialogDestination {

    @Serializable
    data object Dismissed : ProjectDetailsDialogDestination

    @Serializable
    data object Rename : ProjectDetailsDialogDestination

    @Serializable
    data object Delete : ProjectDetailsDialogDestination
}
