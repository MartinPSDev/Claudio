package com.anthropic.claude.audio

/**
 * Thrown when microphone audio capture fails or is unavailable.
 */
class MicrophoneAudioException(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause)
