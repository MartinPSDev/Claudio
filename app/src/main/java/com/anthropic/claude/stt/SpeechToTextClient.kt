package com.anthropic.claude.stt

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Client for Anthropic's speech-to-text API.
 *
 * Sends raw PCM audio data to the STT endpoint and returns
 * transcription results with optional word-level timestamps.
 *
 * The API accepts audio in chunks (streaming) or as a complete recording.
 */
class SpeechToTextClient(
    private val httpClient: OkHttpClient,
    private val baseUrl: String,
) {
    companion object {
        private const val TAG = "SpeechToText"
        private const val STT_ENDPOINT = "/api/voice/stt"
        private val AUDIO_MEDIA_TYPE = "audio/pcm".toMediaType()
    }

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Sends a complete audio recording for transcription.
     *
     * @param audioData Raw PCM audio bytes (16kHz, mono, 16-bit).
     * @param languageHint Optional BCP-47 language hint (e.g., "en-US").
     * @return Transcription result, or null on failure.
     */
    suspend fun transcribe(
        audioData: ByteArray,
        languageHint: String? = null,
    ): TranscriptionResult? {
        return try {
            val urlBuilder = StringBuilder("$baseUrl$STT_ENDPOINT")
            if (languageHint != null) {
                urlBuilder.append("?language=$languageHint")
            }

            val request = Request.Builder()
                .url(urlBuilder.toString())
                .post(audioData.toRequestBody(AUDIO_MEDIA_TYPE))
                .build()

            val response = httpClient.newCall(request).execute()

            if (!response.isSuccessful) {
                Log.e(TAG, "STT request failed: ${response.code}")
                return null
            }

            val body = response.body?.string() ?: return null
            json.decodeFromString<TranscriptionResult>(body)
        } catch (e: Exception) {
            Log.e(TAG, "STT error", e)
            null
        }
    }
}

// ── Models ───────────────────────────────────────────────────────────────

@Serializable
data class TranscriptionResult(
    val text: String,
    val language: String? = null,
    val segments: List<TranscriptionSegment> = emptyList(),
    val durationMs: Long? = null,
)

@Serializable
data class TranscriptionSegment(
    val text: String,
    val startMs: Long,
    val endMs: Long,
    val confidence: Float? = null,
)
