package com.anthropic.claude.artifact.dialog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Parameters passed to the Publish Artifact dialog/flow
 */
data class PublishArtifactParams(
    val messageId: String? = null,
    @SerialName("conversation_uuid") val conversationUuid: String? = null,
    val artifactBody: String? = null,
    val artifactMetadata: JsonElement? = null,
    val artifactVersionIndex: Int? = null,
    val isWiggleArtifact: Boolean = false,
) {
    override fun toString(): String =
        "PublishArtifactParams(messageId=$messageId, conversationUuid=$conversationUuid, " +
        "artifactVersionIndex=$artifactVersionIndex, isWiggleArtifact=$isWiggleArtifact)"
}
