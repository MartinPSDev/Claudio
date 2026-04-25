package com.anthropic.claude.bell

import kotlinx.serialization.Serializable

/**
 * Playback pace for TTS voice output.
 */
@Serializable
enum class PlaybackPace(val speedFactor: Float) {
    SLOW(0.75f),
    NORMAL(1.0f),
    FAST(1.5f);

    companion object {
        val DEFAULT = NORMAL
    }
}

/**
 * Bell overlay sheet destinations.
 */
@Serializable
sealed class BellOverlayDestination {
    @Serializable
    data object Closed : BellOverlayDestination()

    @Serializable
    data object Settings : BellOverlayDestination()

    @Serializable
    data object ViewAllSources : BellOverlayDestination()

    @Serializable
    data class ToolApproval(
        val toolName: String? = null,
        val toolInput: String? = null
    ) : BellOverlayDestination()
}
