package com.anthropic.claude.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.chat.ChatConversation
import com.anthropic.claude.api.chat.MoveChatsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the chat list screen. */
data class ChatListUiState(
    val conversations: List<ChatConversation> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null,
    val selectedIds: Set<String> = emptySet(),
)

/**
 * ViewModel for the all-chats list screen.
 * Manages conversation list, search, selection, and batch operations.
 */
class ChatListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    fun loadConversations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getChats()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isBlank()) return
        viewModelScope.launch {
            // TODO: apiClient.searchConversations(query)
        }
    }

    fun toggleSelection(chatId: String) {
        val current = _uiState.value.selectedIds
        _uiState.value = _uiState.value.copy(
            selectedIds = if (chatId in current) current - chatId else current + chatId,
        )
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedIds = emptySet())
    }

    fun deleteSelected() {
        viewModelScope.launch {
            val ids = _uiState.value.selectedIds.toList()
            // TODO: apiClient.deleteChats(ids)
            clearSelection()
            loadConversations()
        }
    }

    fun moveSelectedToProject(projectId: String) {
        viewModelScope.launch {
            val ids = _uiState.value.selectedIds.toList()
            // TODO: apiClient.moveChats(MoveChatsRequest(chatIds = ids, projectId = projectId))
            clearSelection()
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
