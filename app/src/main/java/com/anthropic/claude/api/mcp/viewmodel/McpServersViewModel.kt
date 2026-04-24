package com.anthropic.claude.api.mcp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.mcp.McpServer
import com.anthropic.claude.api.mcp.McpTool
import com.anthropic.claude.api.mcp.CreateMcpRemoteServerRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the MCP servers screen. */
data class McpServersUiState(
    val servers: List<McpServer> = emptyList(),
    val tools: List<McpTool> = emptyList(),
    val isLoading: Boolean = false,
    val isConnecting: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the MCP server management screen.
 * Handles listing, connecting, and disconnecting MCP servers.
 */
class McpServersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(McpServersUiState())
    val uiState: StateFlow<McpServersUiState> = _uiState.asStateFlow()

    fun loadServers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getMcpServers()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun addRemoteServer(request: CreateMcpRemoteServerRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isConnecting = true)
            // TODO: apiClient.createMcpRemoteServer(request)
            _uiState.value = _uiState.value.copy(isConnecting = false)
            loadServers()
        }
    }

    fun removeServer(serverId: String) {
        viewModelScope.launch {
            // TODO: apiClient.deleteMcpServer(serverId)
            loadServers()
        }
    }

    fun loadToolsForServer(serverId: String) {
        viewModelScope.launch {
            // TODO: apiClient.getMcpServerTools(serverId)
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
