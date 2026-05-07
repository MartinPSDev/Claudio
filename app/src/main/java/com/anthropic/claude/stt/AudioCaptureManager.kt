package com.anthropic.claude.stt

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

/**
 * Captures raw PCM audio from the device microphone for speech-to-text processing.
 *
 * Audio configuration:
 *   - Sample rate: 16kHz (optimal for STT models)
 *   - Channel: Mono
 *   - Encoding: 16-bit PCM
 *   - Buffer: 2x minimum buffer size for stability
 *
 * Emits audio chunks as [ByteArray] via a [Flow].
 * The caller is responsible for sending chunks to the STT endpoint.
 */
class AudioCaptureManager(
    private val context: Context,
) {
    companion object {
        private const val TAG = "AudioCapture"
        const val SAMPLE_RATE = 16000
        private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }

    private var audioRecord: AudioRecord? = null

    val isRecordingPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO,
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * Starts capturing audio and emits PCM chunks.
     *
     * @return Flow of raw PCM byte arrays.
     * @throws SecurityException if RECORD_AUDIO permission is not granted.
     * @throws com.anthropic.claude.audio.MicrophoneAudioException if microphone init fails.
     */
    fun startCapture(): Flow<ByteArray> = flow {
        if (!isRecordingPermissionGranted) {
            throw SecurityException("RECORD_AUDIO permission not granted")
        }

        val bufferSize = AudioRecord.getMinBufferSize(
            SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT,
        ) * 2

        if (bufferSize <= 0) {
            throw com.anthropic.claude.audio.MicrophoneAudioException(
                "Failed to calculate audio buffer size"
            )
        }

        val record = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            bufferSize,
        )

        if (record.state != AudioRecord.STATE_INITIALIZED) {
            record.release()
            throw com.anthropic.claude.audio.MicrophoneAudioException(
                "AudioRecord failed to initialize"
            )
        }

        audioRecord = record
        record.startRecording()
        Log.i(TAG, "Audio capture started (${SAMPLE_RATE}Hz, mono, 16-bit)")

        try {
            val buffer = ByteArray(bufferSize)
            while (coroutineContext.isActive) {
                val bytesRead = record.read(buffer, 0, bufferSize)
                if (bytesRead > 0) {
                    emit(buffer.copyOf(bytesRead))
                } else if (bytesRead < 0) {
                    Log.e(TAG, "AudioRecord.read returned error: $bytesRead")
                    break
                }
            }
        } finally {
            stopCapture()
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Stops audio capture and releases the microphone.
     */
    fun stopCapture() {
        audioRecord?.let { record ->
            try {
                if (record.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    record.stop()
                }
                record.release()
                Log.i(TAG, "Audio capture stopped")
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping audio capture", e)
            }
        }
        audioRecord = null
    }
}
