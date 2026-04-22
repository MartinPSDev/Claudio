package com.anthropic.claude.artifact.model

import com.anthropic.claude.types.strings.ArtifactId
import com.anthropic.claude.types.strings.ArtifactIdentifier
import kotlinx.serialization.Serializable

/** Opaque UUID that uniquely identifies an artifact version. */
@JvmInline
@Serializable
value class ArtifactUuid(val value: String) {
    override fun toString(): String = value
}

/**
 * Metadata describing a single artifact produced by Claude.
 * Artifacts are versioned, typed content blocks (code, HTML, diagrams, etc.)
 * rendered in a dedicated panel alongside the conversation.
 */
@Serializable
data class ArtifactMetadata(
    val uuid: ArtifactUuid,
    val versionUuid: ArtifactId,
    val identifier: ArtifactIdentifier,
    val type: ArtifactType? = null,
    val title: String? = null,
    val language: String? = null,
    val isComplete: Boolean = false,
)
