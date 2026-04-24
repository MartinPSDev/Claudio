package com.anthropic.claude.api.orbit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.orbit.OrbitModels
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the Orbit assistant overlay. */
data class OrbitUiState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val pendingAction: String? = null,
    val error: String? = null,
)

/**
 * ViewModel for the Orbit assistant overlay.
 * Manages Orbit trigger events and action dispatch from push notifications.
 */
class OrbitViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrbitUiState())
    val uiState: StateFlow<OrbitUiState> = _uiState.asStateFlow()

    fun activateOrbit(conversationUuid: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: load orbit conversation via apiClient.getOrbitConversation(conversationUuid)
            _uiState.value = _uiState.value.copy(isLoading = false, isActive = true)
        }
    }

    fun dismissOrbit() {
        _uiState.value = _uiState.value.copy(isActive = false, pendingAction = null)
    }

    fun updateOrbitSettings(enabled: Boolean, timezone: String?) {
        viewModelScope.launch {
            // TODO: apiClient.updateOrbitSettings(UpdateOrbitSettingsRequest(enabled, timezone))
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
