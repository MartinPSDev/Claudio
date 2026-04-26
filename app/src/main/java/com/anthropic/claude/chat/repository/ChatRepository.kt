package com.anthropic.claude.chat.repository

import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.messages.StreamEvent
import kotlinx.coroutines.flow.Flow

/**
 * Handles communication with the Anthropic API for chat functions.
 */
interface ChatRepository {
    
    /** Creates a new chat and streams the response. */
    fun createChat(request: CreateChatRequest): Flow<StreamEvent>
    
    /** Sends a message to an existing chat and streams the response. */
    fun sendMessage(chatId: String, request: ChatCompletionRequest): Flow<StreamEvent>
}
