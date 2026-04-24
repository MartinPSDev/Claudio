package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Parameters to update a remote session's metadata. */
@Serializable
data class UpdateSessionParams(
    val title: String? = null,
)

/** Parameters for generating a conversation title and git branch name. */
@Serializable
data class GenerateTitleAndBranchParams(
    @SerialName("first_session_message") val firstSessionMessage: String? = null,
)
