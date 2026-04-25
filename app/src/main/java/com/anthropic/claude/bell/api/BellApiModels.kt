package com.anthropic.claude.bell.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// ─── Server → Client messages ─────────────────────────────────────────────────

/**
 * Messages sent from the Bell (voice/audio) server to the client.
 * Sealed hierarchy from BellApiServerMessage smali files.
 */
sealed interface BellApiServerMessage {

    /**
     * Server-side session configuration sent at the start of a Bell session.
     * Contains voice settings, model params, etc.
     */
    @Serializable
    @SerialName("session_config")
    data class SessionServerConfig(
        val data: JsonElement? = null,
    ) : BellApiServerMessage

    /**
     * Signals that the model has finished generating the current turn.
     */
    @Serializable
    @SerialName("message_complete")
    data class MessageCompleteData(
        val data: JsonElement? = null,
    ) : BellApiServerMessage

    /** Catch-all for unrecognized server message types. */
    data class Unknown(val raw: JsonElement? = null) : BellApiServerMessage
}

// ─── Client → Server messages ─────────────────────────────────────────────────

/**
 * Messages sent from the client to the Bell (voice/audio) server.
 * Sealed hierarchy from BellApiClientMessage smali files.
 */
sealed interface BellApiClientMessage {

    /**
     * Client voice + speed selection sent when starting or switching voices.
     */
    @Serializable
    @SerialName("voice_select")
    data class VoiceSelect(
        val data: Data,
    ) : BellApiClientMessage {

        @Serializable
        data class Data(
            val voice: String,
            val speed: Float = 1.0f,
        )
    }

    /**
     * Signals the end of an attachment upload flow.
     */
    @Serializable
    @SerialName("attachment_flow_end")
    data class AttachmentFlowEnd(
        val data: Data,
    ) : BellApiClientMessage {

        @Serializable
        data class Data(
            @SerialName("attachment_id") val attachmentId: String? = null,
            val status: String? = null,
        )
    }
}

// ─── Microphone exception ─────────────────────────────────────────────────────

/**
 * Thrown when the microphone audio capture fails.
 */
class MicrophoneAudioException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
