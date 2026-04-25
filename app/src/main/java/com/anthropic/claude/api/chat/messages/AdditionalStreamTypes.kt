package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.Serializable

/**
 * Bell voice note content block — contains audio transcription data.
 */
@Serializable
data class BellNoteBlock(
    val start_timestamp: String? = null,
    val stop_timestamp: String? = null,
    val text: String? = null
) : ContentBlock

/**
 * Unknown/fallback stream event for forward-compatibility.
 */
@Serializable
data class UnknownStreamEvent(
    val type: String? = null
) : StreamEvent
