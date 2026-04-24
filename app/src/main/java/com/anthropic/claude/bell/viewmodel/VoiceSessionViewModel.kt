package com.anthropic.claude.bell.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.bell.VoiceSessionSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Playback state for TTS output. */
enum class TtsPlaybackState { IDLE, PLAYING, PAUSED, ERROR }

/** UI state for the voice/Bell mode session. */
data class VoiceSessionUiState(
    val isListening: Boolean = false,
    val isSpeaking: Boolean = false,
    val ttsState: TtsPlaybackState = TtsPlaybackState.IDLE,
    val transcript: String = "",
    val sessionSummary: VoiceSessionSummary? = null,
    val error: String? = null,
)

/**
 * ViewModel for the Bell voice assistant mode.
 * Manages microphone listening, STT transcript, and TTS playback.
 */
class VoiceSessionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VoiceSessionUiState())
    val uiState: StateFlow<VoiceSessionUiState> = _uiState.asStateFlow()

    fun startListening() {
        _uiState.value = _uiState.value.copy(isListening = true, transcript = "")
        // TODO: bind to ClaudeRecognitionService
    }

    fun stopListening() {
        _uiState.value = _uiState.value.copy(isListening = false)
        val transcript = _uiState.value.transcript
        if (transcript.isNotBlank()) sendVoiceMessage(transcript)
    }

    fun onTranscriptUpdated(partial: String) {
        _uiState.value = _uiState.value.copy(transcript = partial)
    }

    private fun sendVoiceMessage(text: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSpeaking = true)
            // TODO: send to ChatViewModel / AnthropicApiClient and stream TTS via TTSPlaybackService
            _uiState.value = _uiState.value.copy(isSpeaking = false)
        }
    }

    fun pauseTts() {
        _uiState.value = _uiState.value.copy(ttsState = TtsPlaybackState.PAUSED)
        // TODO: TTSPlaybackService.pause()
    }

    fun resumeTts() {
        _uiState.value = _uiState.value.copy(ttsState = TtsPlaybackState.PLAYING)
        // TODO: TTSPlaybackService.resume()
    }

    fun endSession() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isListening = false, isSpeaking = false)
            // TODO: finalize session summary via BellModeService
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
