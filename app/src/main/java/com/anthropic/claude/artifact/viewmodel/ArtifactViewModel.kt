package com.anthropic.claude.artifact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.artifact.model.ArtifactMetadata
import com.anthropic.claude.artifact.model.ArtifactType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the artifact viewer. */
data class ArtifactUiState(
    val artifact: ArtifactMetadata? = null,
    val isLoading: Boolean = false,
    val isDownloading: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the artifact full-screen viewer.
 * Handles loading, rendering, and downloading artifacts.
 */
class ArtifactViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArtifactUiState())
    val uiState: StateFlow<ArtifactUiState> = _uiState.asStateFlow()

    fun loadArtifact(artifactId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getArtifact(artifactId)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun downloadArtifact() {
        viewModelScope.launch {
            val artifact = _uiState.value.artifact ?: return@launch
            _uiState.value = _uiState.value.copy(isDownloading = true)
            // TODO: download file based on artifact.type and artifact.url
            _uiState.value = _uiState.value.copy(isDownloading = false)
        }
    }

    fun shareArtifact() {
        viewModelScope.launch {
            // TODO: generate share link via apiClient.createChatSnapshot(...)
        }
    }

    fun remixArtifact(artifactId: String) {
        viewModelScope.launch {
            // TODO: create new chat with artifact as attachment
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
