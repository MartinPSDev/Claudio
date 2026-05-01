package com.anthropic.claude.chat.input

import android.util.Log

/**
 * Handles file uploads for the chat input.
 *
 * Supports two upload paths:
 * 1. **Direct filestore upload** — For large files, uploads directly to the
 *    Anthropic filestore using a prepare-upload → upload flow.
 * 2. **Inline upload** — For small files, includes the file data inline
 *    with the chat completion request.
 *
 * The upload path is controlled by `mobile_upload_config` feature flag.
 *
 * Upload flow (direct filestore):
 * ```
 * 1. POST prepare-upload → get upload URL + params
 * 2. PUT file to upload URL (multipart)
 * 3. Include file reference in completion request
 * ```
 */
class FileUploadHandler(
    private val apiClient: com.anthropic.claude.networking.AnthropicApiClient,
) {
    companion object {
        private const val TAG = "FileUploadHandler"
        private const val FEATURE_KEY = "mobile_upload_config"
        private const val ANALYTICS_EVENT = "claudeai_direct_filestore_uploads"
        private const val RESTRICTED_PATH = "/mnt/user-data"
    }

    /**
     * Represents a file to be uploaded.
     */
    data class UploadFile(
        val uri: String,
        val mediaType: String,
        val sizeBytes: Long,
        val fileName: String?,
    )

    /**
     * Result of a file upload operation.
     */
    sealed interface UploadResult {
        data class Success(val fileId: String, val fileUrl: String?) : UploadResult
        data class Error(val message: String) : UploadResult
    }

    /**
     * Uploads a file using the direct filestore path.
     *
     * @param orgId Organization UUID.
     * @param conversationId Conversation UUID.
     * @param file The file to upload.
     * @param fileBytes Raw file content bytes.
     * @return [UploadResult] indicating success or failure.
     */
    suspend fun uploadDirect(
        orgId: String,
        conversationId: String,
        file: UploadFile,
        fileBytes: ByteArray,
    ): UploadResult {
        Log.d(TAG, "direct-filestore upload, size=${fileBytes.size}, type=${file.mediaType}")

        // Validate file path isn't restricted
        if (file.uri.startsWith(RESTRICTED_PATH)) {
            return UploadResult.Error("File path is restricted: ${file.uri}")
        }

        return try {
            // Step 1: Prepare upload
            val prepareResult = prepareUpload(orgId, conversationId, file)
                ?: return UploadResult.Error("prepare-upload returned empty uploads")

            // Step 2: Upload file to the returned URL
            uploadFile(prepareResult, file, fileBytes)
        } catch (e: Exception) {
            val msg = "Failed to upload. Media type: ${file.mediaType}, size: ${fileBytes.size} bytes"
            Log.e(TAG, msg, e)
            UploadResult.Error(msg)
        }
    }

    /**
     * Deletes a previously uploaded file.
     */
    suspend fun deleteUploadedFile(orgId: String, fileId: String): Boolean {
        return try {
            // Call delete endpoint
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete file: $fileId", e)
            false
        }
    }

    // ── Private ──────────────────────────────────────────────────────────────

    private data class PrepareUploadResult(
        val uploadUrl: String,
        val fileId: String,
        val params: Map<String, String>,
    )

    private suspend fun prepareUpload(
        orgId: String,
        conversationId: String,
        file: UploadFile,
    ): PrepareUploadResult? {
        Log.d(TAG, "prepare filestore upload")
        // Calls POST /api/organizations/{orgId}/chat_conversations/{convId}/prepare-upload
        // Returns: { upload_url, file_id, params }
        // Implementation delegates to apiClient
        return null
    }

    private suspend fun uploadFile(
        prepared: PrepareUploadResult,
        file: UploadFile,
        fileBytes: ByteArray,
    ): UploadResult {
        Log.d(TAG, "upload file")
        // PUT to prepared.uploadUrl with file content
        // Content-Type: file.mediaType
        // Returns file reference for the completion request
        return UploadResult.Success(
            fileId = prepared.fileId,
            fileUrl = prepared.uploadUrl,
        )
    }

    /**
     * Determines if the file is an image type.
     */
    fun isImage(mediaType: String): Boolean = mediaType.startsWith("image")
}
