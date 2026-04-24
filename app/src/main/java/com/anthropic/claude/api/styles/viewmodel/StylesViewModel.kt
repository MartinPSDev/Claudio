package com.anthropic.claude.api.styles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.styles.Style
import com.anthropic.claude.api.styles.CustomStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the styles selector. */
data class StylesUiState(
    val styles: List<Style> = emptyList(),
    val selectedStyleId: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the conversation styles picker.
 */
class StylesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StylesUiState())
    val uiState: StateFlow<StylesUiState> = _uiState.asStateFlow()

    fun loadStyles() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getStyles()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun selectStyle(styleId: String?) {
        _uiState.value = _uiState.value.copy(selectedStyleId = styleId)
        // TODO: persist to conversation settings
    }

    fun createCustomStyle(style: CustomStyle) {
        viewModelScope.launch {
            // TODO: apiClient.createStyle(style)
            loadStyles()
        }
    }

    fun deleteStyle(styleId: String) {
        viewModelScope.launch {
            // TODO: apiClient.deleteStyle(styleId)
            if (_uiState.value.selectedStyleId == styleId) selectStyle(null)
            loadStyles()
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
