package com.anthropic.claude.tool.ui.chat

/**
 * Navigation destinations for the phone call tool UI bottom sheet.
 * Extracted from PhoneCallSheetDestination smali files.
 */
sealed interface PhoneCallSheetDestination {

    /** Show the call transcript. */
    data class Transcript(
        val content: String = "",
    ) : PhoneCallSheetDestination

    /** Sheet closed / dismissed. */
    data object Closed : PhoneCallSheetDestination
}

/**
 * Navigation destinations for the form-input tool UI bottom sheet.
 * Extracted from FormSheetDestination smali files.
 */
sealed interface FormSheetDestination {

    /** Display a form for user input. */
    data class FormInput(
        val formId: String,
        val title: String? = null,
    ) : FormSheetDestination

    /** Sheet closed / dismissed. */
    data object Closed : FormSheetDestination
}
