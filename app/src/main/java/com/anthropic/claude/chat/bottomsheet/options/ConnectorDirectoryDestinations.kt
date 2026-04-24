package com.anthropic.claude.chat.bottomsheet.options

/**
 * Bottom sheet destination for the MCP connector directory.
 */
data object ConnectorDirectory : java.io.Serializable

/**
 * Bottom sheet destination for the MCP connector detail view.
 */
data class ConnectorDirectoryDetail(val connectorId: String? = null)
