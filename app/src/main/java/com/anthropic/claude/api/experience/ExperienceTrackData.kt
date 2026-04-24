package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable

/** Payload sent when an experience is dismissed by the user. */
@Serializable
data class TrackDismissedData(
    val reason: String? = null,
)

/** Payload sent when an experience becomes visible to the user. */
@Serializable
data class TrackShownData(
    val visible: Boolean = true,
)
