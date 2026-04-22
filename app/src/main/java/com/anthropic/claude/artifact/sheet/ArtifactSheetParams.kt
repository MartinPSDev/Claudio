package com.anthropic.claude.artifact.sheet

import com.anthropic.claude.artifact.model.ArtifactIdentifier
import com.anthropic.claude.types.strings.MessageId
import kotlinx.serialization.Serializable

/**
 * Params that describe which artifact is displayed in the bottom sheet panel.
 * Subtypes differentiate between regular in-conversation artifacts and
 * "wiggle" file artifacts surfaced from the project knowledge base.
 */
sealed class ArtifactBottomSheetParams {
    abstract val identifier: ArtifactIdentifier
    abstract val isShareEnabled: Boolean
    abstract val versionIndex: Int

    @Serializable
    data class InMessageArtifact(
        override val identifier: ArtifactIdentifier,
        override val isShareEnabled: Boolean = true,
        override val versionIndex: Int = 0,
    ) : ArtifactBottomSheetParams()

    @Serializable
    data class WiggleFileArtifact(
        override val identifier: ArtifactIdentifier,
        override val isShareEnabled: Boolean = false,
        override val versionIndex: Int = 0,
    ) : ArtifactBottomSheetParams()
}

/** Share-sheet params for a specific artifact version. */
@Serializable
data class ArtifactShareParams(
    val artifactUuid: String,
    val messageId: MessageId,
    val versionIndex: Int = 0,
    val isWiggleArtifact: Boolean = false,
)
