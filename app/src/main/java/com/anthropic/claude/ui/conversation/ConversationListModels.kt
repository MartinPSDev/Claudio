package com.anthropic.claude.ui.conversation

/**
 * Represents the state of the conversation list screen.
 *
 * Supports loading, displaying conversations, empty state, and error handling.
 */
sealed interface ConversationListState {
    /** Initial loading state. */
    data object Loading : ConversationListState

    /** Conversations loaded successfully. */
    data class Loaded(
        val conversations: List<ConversationListItem>,
        val hasMore: Boolean = false,
        val isLoadingMore: Boolean = false,
    ) : ConversationListState

    /** No conversations to display. */
    data object Empty : ConversationListState

    /** Error loading conversations. */
    data class Error(val message: String) : ConversationListState
}

/**
 * A single item in the conversation list.
 */
data class ConversationListItem(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val lastMessageTimestamp: Long,
    val isStarred: Boolean = false,
    val projectId: String? = null,
    val projectName: String? = null,
    val modelId: String? = null,
    val hasAttachments: Boolean = false,
    val messageCount: Int = 0,
)

/**
 * Actions that can be performed on a conversation list item.
 */
sealed interface ConversationListAction {
    data class Open(val conversationId: String) : ConversationListAction
    data class Delete(val conversationId: String) : ConversationListAction
    data class Rename(val conversationId: String, val newTitle: String) : ConversationListAction
    data class ToggleStar(val conversationId: String) : ConversationListAction
    data class MoveToProject(val conversationId: String, val projectId: String) : ConversationListAction
    data class Share(val conversationId: String) : ConversationListAction
}
