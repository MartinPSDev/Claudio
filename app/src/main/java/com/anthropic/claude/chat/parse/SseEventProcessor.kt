package com.anthropic.claude.chat.parse

import com.anthropic.claude.api.chat.messages.StreamEvent

/**
 * Responsible for handling Server-Sent Events from the chat stream.
 */
interface SseEventProcessor {
    /**
     * Consume an incoming stream event and update internal models or UI state accordingly.
     */
    suspend fun processEvent(event: StreamEvent)
}
