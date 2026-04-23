package com.anthropic.claude.api.artifacts

import com.anthropic.claude.types.strings.ArtifactId
import com.anthropic.claude.types.strings.ArtifactIdentifier
import com.anthropic.claude.types.strings.ChatId
import com.anthropic.claude.types.strings.MessageId
import com.anthropic.claude.types.strings.PublishedArtifactId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ─── Enums ───────────────────────────────────────────────────────────────────

/** Where an artifact was originally created. */
@Serializable
enum class ArtifactSource(val value: String) {
    @SerialName("unknown") UNKNOWN("unknown"),
    @SerialName("compass") COMPASS("compass"),
    @SerialName("wiggle") WIGGLE("wiggle"),
}

/** Whether an artifact is publicly shared or private. */
@Serializable
enum class ArtifactVisibility(val value: String) {
    @SerialName("unknown") UNKNOWN("unknown"),
    @SerialName("shared") SHARED("shared"),
    @SerialName("private") PRIVATE("private"),
}

// ─── Data classes ─────────────────────────────────────────────────────────────

/**
 * A specific version snapshot of an artifact.
 * Each save produces a new [ArtifactVersionRecord].
 */
@Serializable
data class ArtifactVersionRecord(
    val uuid: ArtifactId? = null,
    @SerialName("artifact_uuid") val artifactUuid: ArtifactId? = null,
    @SerialName("message_uuid") val messageUuid: MessageId? = null,
    @SerialName("artifact_type") val artifactType: String? = null,
    @SerialName("code_language") val codeLanguage: String? = null,
    val title: String? = null,
    @SerialName("result_state") val resultState: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val source: ArtifactSource? = null,
    val visibility: ArtifactVisibility? = null,
    @SerialName("published_artifact_uuid") val publishedArtifactUuid: PublishedArtifactId? = null,
    @SerialName("published_artifact_deleted_at") val publishedArtifactDeletedAt: String? = null,
)

/**
 * Paginated list of artifact version records.
 */
@Serializable
data class ArtifactVersionsResponse(
    @SerialName("artifact_versions") val artifactVersions: List<ArtifactVersionRecord> = emptyList(),
)

/**
 * A user-facing artifact (the canonical representation, not a specific version).
 */
@Serializable
data class UserArtifact(
    val uuid: ArtifactId? = null,
    @SerialName("artifact_identifier") val artifactIdentifier: ArtifactIdentifier? = null,
    @SerialName("latest_published_artifact_uuid") val latestPublishedArtifactUuid: PublishedArtifactId? = null,
    @SerialName("artifact_type") val artifactType: String? = null,
    val title: String? = null,
    @SerialName("code_language") val codeLanguage: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("chat_conversation_uuid") val chatConversationUuid: ChatId? = null,
    val preview: String? = null,
)

/**
 * Paginated list of the user's artifacts.
 */
@Serializable
data class UserArtifactsListResponse(
    val artifacts: List<UserArtifact> = emptyList(),
)

/**
 * An artifact that has been shared publicly.
 */
@Serializable
data class PublishedArtifact(
    @SerialName("artifact_identifier") val artifactIdentifier: ArtifactIdentifier? = null,
    @SerialName("published_artifact_uuid") val publishedArtifactUuid: PublishedArtifactId? = null,
    @SerialName("artifact_type") val artifactType: String? = null,
    @SerialName("code_language") val codeLanguage: String? = null,
    @SerialName("message_uuid") val messageUuid: MessageId? = null,
    val title: String? = null,
    val deleted: Boolean = false,
)

/**
 * Request body to publish or update a shared artifact.
 */
@Serializable
data class PublishArtifactRequest(
    @SerialName("artifact_identifier") val artifactIdentifier: ArtifactIdentifier,
    @SerialName("artifact_type") val artifactType: String,
    @SerialName("artifact_version_uuid") val artifactVersionUuid: ArtifactId? = null,
    @SerialName("code_language") val codeLanguage: String? = null,
    val content: String,
    @SerialName("message_uuid") val messageUuid: MessageId,
    val title: String,
)

/**
 * Request body to update the visibility of an artifact.
 */
@Serializable
data class UpdateVisibilityRequest(
    val visibility: ArtifactVisibility,
)
