package com.anthropic.claude.artifact.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Discriminated union representing the type of an Artifact.
 * Each subtype carries a [mimeType] string used for rendering.
 */
@Serializable
@JsonClassDiscriminator("type")
sealed class ArtifactType {

    abstract val mimeType: String

    @Serializable
    @SerialName("text/plain")
    data object Text : ArtifactType() {
        override val mimeType = "text/plain"
    }

    @Serializable
    @SerialName("text/markdown")
    data object Markdown : ArtifactType() {
        override val mimeType = "text/markdown"
    }

    @Serializable
    @SerialName("text/html")
    data object Html : ArtifactType() {
        override val mimeType = "text/html"
    }

    @Serializable
    @SerialName("image/svg+xml")
    data object Svg : ArtifactType() {
        override val mimeType = "image/svg+xml"
    }

    @Serializable
    @SerialName("application/vnd.ant.code")
    data object Code : ArtifactType() {
        override val mimeType = "application/vnd.ant.code"
    }

    @Serializable
    @SerialName("application/vnd.ant.mermaid")
    data object Mermaid : ArtifactType() {
        override val mimeType = "application/vnd.ant.mermaid"
    }

    @Serializable
    @SerialName("application/vnd.ant.react")
    data object React : ArtifactType() {
        override val mimeType = "application/vnd.ant.react"
    }

    @Serializable
    @SerialName("application/vnd.ant.repl")
    data object Repl : ArtifactType() {
        override val mimeType = "application/vnd.ant.repl"
    }

    @Serializable
    @SerialName("application/vnd.ant.binary")
    data object BinaryDocument : ArtifactType() {
        override val mimeType = "application/vnd.ant.binary"
    }
}
