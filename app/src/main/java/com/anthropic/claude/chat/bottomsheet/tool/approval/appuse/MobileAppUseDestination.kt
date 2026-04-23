package com.anthropic.claude.chat.bottomsheet.tool.approval.appuse

/**
 * Navigation destinations for the mobile app-use tool approval bottom sheet.
 */
sealed interface MobileAppUseDestination {

    /**
     * Show the main approval screen for the app-use tool action.
     */
    data object Approval : MobileAppUseDestination

    /**
     * Show the calendar event selection screen within the approval flow.
     */
    data object CalendarSelection : MobileAppUseDestination
}
