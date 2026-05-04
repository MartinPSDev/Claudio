package com.anthropic.claude.ui.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.repository.ConversationRepository
import com.anthropic.claude.util.ChatDateFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the conversation list / home screen.
 *
 * Loads conversations from the repository, supports pagination,
 * search filtering, and conversation management actions.
 */
class ConversationListViewModel(
    private val conversationRepository: ConversationRepository,
    private val orgId: String,
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
    }

    private val _state = MutableStateFlow<ConversationListState>(ConversationListState.Loading)
    val state: StateFlow<ConversationListState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var currentCursor: String? = null

    init {
        loadConversations()
    }

    fun loadConversations() {
        viewModelScope.launch {
            _state.value = ConversationListState.Loading
            try {
                val result = conversationRepository.listConversations(
                    orgId = orgId,
                    limit = PAGE_SIZE,
                    cursor = null,
                )

                val items = result.conversations.map { it.toListItem() }
                currentCursor = result.nextCursor

                _state.value = if (items.isEmpty()) {
                    ConversationListState.Empty
                } else {
                    ConversationListState.Loaded(
                        conversations = items,
                        hasMore = result.hasMore,
                    )
                }
            } catch (e: Exception) {
                _state.value = ConversationListState.Error(
                    e.message ?: "Failed to load conversations"
                )
            }
        }
    }

    fun loadMore() {
        val current = _state.value
        if (current !is ConversationListState.Loaded || !current.hasMore || current.isLoadingMore) return

        _state.value = current.copy(isLoadingMore = true)

        viewModelScope.launch {
            try {
                val result = conversationRepository.listConversations(
                    orgId = orgId,
                    limit = PAGE_SIZE,
                    cursor = currentCursor,
                )

                val newItems = result.conversations.map { it.toListItem() }
                currentCursor = result.nextCursor

                _state.value = current.copy(
                    conversations = current.conversations + newItems,
                    hasMore = result.hasMore,
                    isLoadingMore = false,
                )
            } catch (e: Exception) {
                _state.value = current.copy(isLoadingMore = false)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // Filter is applied in the UI layer for instant filtering
    }

    fun onAction(action: ConversationListAction) {
        viewModelScope.launch {
            when (action) {
                is ConversationListAction.Delete -> {
                    conversationRepository.deleteConversation(orgId, action.conversationId)
                    removeFromList(action.conversationId)
                }
                is ConversationListAction.Rename -> {
                    conversationRepository.renameConversation(
                        orgId, action.conversationId, action.newTitle,
                    )
                    loadConversations()
                }
                is ConversationListAction.ToggleStar -> {
                    toggleStar(action.conversationId)
                }
                is ConversationListAction.MoveToProject -> {
                    // Move to project API call
                    loadConversations()
                }
                is ConversationListAction.Open,
                is ConversationListAction.Share -> {
                    // Handled by the UI layer
                }
            }
        }
    }

    private fun removeFromList(conversationId: String) {
        val current = _state.value
        if (current is ConversationListState.Loaded) {
            val filtered = current.conversations.filter { it.id != conversationId }
            _state.value = if (filtered.isEmpty()) {
                ConversationListState.Empty
            } else {
                current.copy(conversations = filtered)
            }
        }
    }

    private suspend fun toggleStar(conversationId: String) {
        val current = _state.value
        if (current is ConversationListState.Loaded) {
            val updated = current.conversations.map {
                if (it.id == conversationId) it.copy(isStarred = !it.isStarred) else it
            }
            _state.value = current.copy(conversations = updated)
        }
    }

    private fun com.anthropic.claude.db.entity.ConversationEntity.toListItem(): ConversationListItem {
        return ConversationListItem(
            id = this.uuid,
            title = this.name.ifEmpty { "Untitled" },
            subtitle = this.summary,
            lastMessageTimestamp = this.updatedAt,
            isStarred = this.isStarred,
            projectId = this.projectUuid,
            modelId = this.modelId,
        )
    }
}
