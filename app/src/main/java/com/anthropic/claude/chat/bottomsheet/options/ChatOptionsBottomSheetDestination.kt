package com.anthropic.claude.chat.bottomsheet.options

/**
 * Navigation destinations for the chat screen options bottom sheet.
 */
sealed interface ChatOptionsBottomSheetDestination {

    /** Select which tools the model is allowed to use. */
    data object SelectToolAccess : ChatOptionsBottomSheetDestination

    /** Select a writing style for the conversation. */
    data object SelectStyle : ChatOptionsBottomSheetDestination

    /** Select a project to associate with the chat. */
    data object SelectProject : ChatOptionsBottomSheetDestination

    /** Browse the full MCP connector directory. */
    data object ConnectorDirectory : ChatOptionsBottomSheetDestination

    /** Browse all connected MCP servers. */
    data object Connectors : ChatOptionsBottomSheetDestination

    /** Show detail view for a specific connector server. */
    data class ConnectorDirectoryDetail(
        val server: String,
    ) : ChatOptionsBottomSheetDestination

    /** Show tools available from a specific MCP server. */
    data class McpServerTools(
        val serverId: String,
    ) : ChatOptionsBottomSheetDestination

    /** Show a specific MCP prompt template. */
    data class McpPromptTemplate(
        val serverId: String,
        val promptName: String? = null,
    ) : ChatOptionsBottomSheetDestination

    /** Sheet closed / dismissed. */
    data object Closed : ChatOptionsBottomSheetDestination
}
