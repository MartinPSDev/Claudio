package com.anthropic.claude.chat.bottomsheet.model

/**
 * Navigation destinations for the model-selector bottom sheet.
 */
sealed interface ModelSelectorBottomSheetDestination {
    /** Sheet closed / dismissed. */
    data object Closed : ModelSelectorBottomSheetDestination
    /** Main model selection list. */
    data class SelectModel(val currentModelId: String? = null) : ModelSelectorBottomSheetDestination
    /** Show all available models (expanded view). */
    data class MoreModels(val currentModelId: String? = null) : ModelSelectorBottomSheetDestination
}
