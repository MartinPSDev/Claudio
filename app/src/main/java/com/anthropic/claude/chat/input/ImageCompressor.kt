package com.anthropic.claude.chat.input

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream

/**
 * Handles image compression before upload.
 *
 * Supported formats: PNG, JPEG, WebP.
 * Images that are already within the size threshold are not re-compressed.
 *
 * Compression strategy:
 * 1. Check if the image can skip compression (already small enough).
 * 2. Decode the bitmap.
 * 3. Compress to JPEG at a configurable quality level.
 * 4. Return the compressed bytes.
 */
object ImageCompressor {

    private const val TAG = "ImageCompressor"

    /**
     * MIME types that are eligible for compression.
     */
    val COMPRESSIBLE_TYPES = setOf(
        "image/png",
        "image/webp",
        "image/jpg",
        "image/jpeg",
    )

    /** Default maximum file size before compression is applied (5 MB). */
    private const val DEFAULT_MAX_SIZE_BYTES = 5 * 1024 * 1024L

    /** Default JPEG compression quality (0-100). */
    private const val DEFAULT_QUALITY = 85

    /**
     * Compresses an image if it exceeds the maximum size threshold.
     *
     * @param imageBytes The original image bytes.
     * @param mimeType The image MIME type.
     * @param maxSizeBytes Maximum size before compression is applied.
     * @param quality JPEG compression quality (0-100).
     * @return Compressed image bytes, or the original if compression is not needed.
     */
    fun compressIfNeeded(
        imageBytes: ByteArray,
        mimeType: String,
        maxSizeBytes: Long = DEFAULT_MAX_SIZE_BYTES,
        quality: Int = DEFAULT_QUALITY,
    ): ByteArray {
        if (!canSkipCompression(imageBytes, mimeType, maxSizeBytes)) {
            return compress(imageBytes, quality)
        }
        return imageBytes
    }

    /**
     * Returns true if the image does not need compression.
     */
    fun canSkipCompression(
        imageBytes: ByteArray,
        mimeType: String,
        maxSizeBytes: Long = DEFAULT_MAX_SIZE_BYTES,
    ): Boolean {
        return try {
            if (!COMPRESSIBLE_TYPES.contains(mimeType)) return true
            imageBytes.size <= maxSizeBytes
        } catch (e: Exception) {
            Log.w(TAG, "Failed checking if image can skip compression", e)
            false
        }
    }

    /**
     * Compresses the image bytes to JPEG format.
     *
     * @param imageBytes Original image data.
     * @param quality JPEG quality (0-100).
     * @return Compressed JPEG bytes.
     */
    fun compress(imageBytes: ByteArray, quality: Int = DEFAULT_QUALITY): ByteArray {
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ?: return imageBytes

        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output)
        bitmap.recycle()

        return output.toByteArray()
    }

    /**
     * Compresses a bitmap to JPEG bytes at the given quality.
     */
    fun compressBitmap(bitmap: Bitmap, quality: Int = DEFAULT_QUALITY): ByteArray {
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output)
        return output.toByteArray()
    }
}
