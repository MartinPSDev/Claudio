package com.anthropic.claude.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.api.chat.ChatConversation
import com.anthropic.claude.api.chat.ChatMessage
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.UpdateChatRequest
import com.anthropic.claude.api.chat.RecordToolApprovalRequest
import com.anthropic.claude.api.chat.messages.ContentBlock
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the chat screen. */
data class ChatUiState(
    val conversation: ChatConversation? = null,
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val isStreaming: Boolean = false,
    val error: String? = null,
    val inputText: String = "",
    val pendingToolApproval: RecordToolApprovalRequest? = null,
)

/**
 * ViewModel for the chat screen.
 * Manages conversation state, message streaming, and tool approval flows.
 */
class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    // ── Conversation ──────────────────────────────────────────────────────────

    fun loadConversation(chatId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: inject AnthropicApiClient and load conversation
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun createNewConversation(projectId: String? = null, initialPrompt: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: call apiClient.createChat(CreateChatRequest(...))
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    // ── Messaging ─────────────────────────────────────────────────────────────

    fun onInputChanged(text: String) {
        _uiState.value = _uiState.value.copy(inputText = text)
    }

    fun sendMessage() {
        val text = _uiState.value.inputText.trim()
        if (text.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isStreaming = true,
                inputText = "",
            )
            // TODO: stream via MessageSseService / ChatCompletionRequest
            _uiState.value = _uiState.value.copy(isStreaming = false)
        }
    }

    fun stopStreaming() {
        _uiState.value = _uiState.value.copy(isStreaming = false)
        // TODO: cancel active SSE coroutine job
    }

    // ── Tool Approval ─────────────────────────────────────────────────────────

    fun approveToolUse(toolUseId: String, approvalKey: String) {
        viewModelScope.launch {
            // TODO: call apiClient.recordToolApproval(RecordToolApprovalRequest(...))
            _uiState.value = _uiState.value.copy(pendingToolApproval = null)
        }
    }

    fun denyToolUse(toolUseId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(pendingToolApproval = null)
        }
    }

    // ── Rename / Delete ───────────────────────────────────────────────────────

    fun renameConversation(newTitle: String) {
        viewModelScope.launch {
            val chatId = _uiState.value.conversation?.uuid ?: return@launch
            // TODO: call apiClient.updateChat(chatId, UpdateChatRequest(title = newTitle))
        }
    }

    fun deleteConversation() {
        viewModelScope.launch {
            val chatId = _uiState.value.conversation?.uuid ?: return@launch
            // TODO: call apiClient.deleteChat(chatId)
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
