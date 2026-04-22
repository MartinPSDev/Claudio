package com.anthropic.claude.project.details.custominstructions

import kotlinx.serialization.Serializable

sealed interface CustomInstructionsDialogRoute {

    @Serializable
    data object Closed : CustomInstructionsDialogRoute

    @Serializable
    data class EditCustomInstructions(
        val existingInstructions: String? = null,
    ) : CustomInstructionsDialogRoute
}
