package com.anthropic.claude.api.wiggle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ─── File upload preparation ──────────────────────────────────────────────────

/** Specifies a single file to prepare for upload (name only). */
@Serializable
data class PrepareUploadFileSpec(
    val name: String,
)

/** Pre-signed upload slot returned for one file in a prepare-upload batch. */
@Serializable
data class PrepareUploadResult(
    @SerialName("file_id") val fileId: String? = null,
    @SerialName("upload_url") val uploadUrl: String? = null,
    @SerialName("fields") val fields: Map<String, String>? = null,
)

/** Response containing a pre-signed upload slot for each requested file. */
@Serializable
data class PrepareUploadResponse(
    val uploads: List<PrepareUploadResult> = emptyList(),
)

// ─── Delete result enum ───────────────────────────────────────────────────────

@Serializable
enum class WiggleDeleteFileResult {
    @SerialName("success") SUCCESS,
    @SerialName("not_found") NOT_FOUND,
    @SerialName("storage_error") STORAGE_ERROR,
    @SerialName("unknown") UNKNOWN,
}

/** Response from the delete-file endpoint. */
@Serializable
data class WiggleDeleteFileResponse(
    @SerialName("file_id") val fileId: String? = null,
    val result: WiggleDeleteFileResult? = null,
)

/** Response wrapping a list of files in the user's Wiggle file store. */
@Serializable
data class WiggleFileListResponse(
    val files: List<WiggleFileItem> = emptyList(),
)

/** A single file entry in the Wiggle file list. */
@Serializable
data class WiggleFileItem(
    @SerialName("file_id") val fileId: String? = null,
    val name: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("size_bytes") val sizeBytes: Long? = null,
)

/** Upload completion response carrying the final file reference. */
@Serializable
data class WiggleFileUploadResponse(
    @SerialName("file_id") val fileId: String? = null,
    val name: String? = null,
)

/** Response from the convert-Wiggle-to-artifact endpoint. */
@Serializable
data class ConvertWiggleToArtifactResponse(
    @SerialName("artifact_id") val artifactId: String? = null,
)
