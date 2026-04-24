package com.anthropic.claude.api.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.model.ModelOption
import com.anthropic.claude.api.model.ThinkingModeOption
import com.anthropic.claude.models.StickyModelSelection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the model selector. */
data class ModelSelectorUiState(
    val availableModels: List<ModelOption> = emptyList(),
    val selectedModelId: String? = null,
    val thinkingMode: ThinkingModeOption? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the model selector bottom sheet.
 * Manages available models, sticky selection, and thinking mode.
 */
class ModelSelectorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ModelSelectorUiState())
    val uiState: StateFlow<ModelSelectorUiState> = _uiState.asStateFlow()

    fun loadModels() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getModelOptions()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun selectModel(modelId: String) {
        _uiState.value = _uiState.value.copy(selectedModelId = modelId)
        viewModelScope.launch {
            // TODO: persist to StickyModelSelection / DataStore
        }
    }

    fun setThinkingMode(mode: ThinkingModeOption?) {
        _uiState.value = _uiState.value.copy(thinkingMode = mode)
    }

    fun applySelection() {
        viewModelScope.launch {
            val modelId = _uiState.value.selectedModelId ?: return@launch
            // TODO: update conversation settings with selected model + thinking mode
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
