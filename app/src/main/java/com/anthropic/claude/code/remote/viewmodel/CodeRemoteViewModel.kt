package com.anthropic.claude.code.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.sessions.types.CreateSessionParams
import com.anthropic.claude.sessions.types.SessionResource
import com.anthropic.claude.sessions.types.UpdateSessionParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Connection state for the code-remote (Claude Code) session. */
enum class CodeRemoteConnectionState { DISCONNECTED, CONNECTING, CONNECTED, ERROR }

/** UI state for the code-remote session screen. */
data class CodeRemoteUiState(
    val session: SessionResource? = null,
    val connectionState: CodeRemoteConnectionState = CodeRemoteConnectionState.DISCONNECTED,
    val isLoading: Boolean = false,
    val error: String? = null,
    val repoUrl: String = "",
    val branchName: String = "",
)

/**
 * ViewModel for the Claude Code remote session screen.
 * Manages session lifecycle, git operations, and PR creation.
 */
class CodeRemoteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CodeRemoteUiState())
    val uiState: StateFlow<CodeRemoteUiState> = _uiState.asStateFlow()

    fun loadSession(sessionId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getSession(sessionId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun createSession(params: CreateSessionParams) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                connectionState = CodeRemoteConnectionState.CONNECTING,
            )
            // TODO: apiClient.createSession(params)
            _uiState.value = _uiState.value.copy(
                connectionState = CodeRemoteConnectionState.CONNECTED,
            )
        }
    }

    fun renameSession(title: String) {
        viewModelScope.launch {
            val sessionId = _uiState.value.session?.id ?: return@launch
            // TODO: apiClient.updateSession(sessionId, UpdateSessionParams(title = title))
        }
    }

    fun endSession() {
        viewModelScope.launch {
            val sessionId = _uiState.value.session?.id ?: return@launch
            // TODO: apiClient.deleteSession(sessionId)
            _uiState.value = _uiState.value.copy(
                session = null,
                connectionState = CodeRemoteConnectionState.DISCONNECTED,
            )
        }
    }

    fun onRepoUrlChanged(url: String) {
        _uiState.value = _uiState.value.copy(repoUrl = url)
    }

    fun onBranchNameChanged(branch: String) {
        _uiState.value = _uiState.value.copy(branchName = branch)
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
