package com.anthropic.claude.api.chat.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** SSE delta marking the end of a citation span. */
@Serializable
data class CitationEndDelta(
    @SerialName("citation_uuid") val citationUuid: String? = null,
)

/** SSE delta carrying an incremental voice note transcription. */
@Serializable
data class VoiceNoteDelta(
    @SerialName("partial_text") val partialText: String? = null,
)

/** SSE delta carrying a partial JSON fragment for a tool-use argument. */
@Serializable
data class InputJsonDelta(
    @SerialName("partial_json") val partialJson: String? = null,
)
