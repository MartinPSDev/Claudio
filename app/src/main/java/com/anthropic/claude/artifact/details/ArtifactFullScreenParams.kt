package com.anthropic.claude.artifact.details

import kotlinx.serialization.Serializable

/**
 * Navigation params for the Artifact full-screen viewer.
 * Three subtypes: a loaded in-conversation artifact, a published artifact,
 * and a shared artifact accessed via a public link.
 */
sealed interface ArtifactFullScreenParams {
    val uuidValue: String

    @Serializable
    data class LoadedArtifactFullScreenParams(
        override val uuidValue: String,
        val versionIndex: Int = 0,
    ) : ArtifactFullScreenParams

    @Serializable
    data class PublishedArtifactFullScreenParams(
        override val uuidValue: String,
    ) : ArtifactFullScreenParams

    @Serializable
    data class SharedArtifactFullScreenParams(
        override val uuidValue: String,
    ) : ArtifactFullScreenParams
}
