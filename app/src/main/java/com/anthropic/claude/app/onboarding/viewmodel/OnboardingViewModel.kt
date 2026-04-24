package com.anthropic.claude.app.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.app.onboarding.v2.OnboardingPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the onboarding flow. */
data class OnboardingUiState(
    val currentPage: OnboardingPage = OnboardingPage.Welcome,
    val isLoading: Boolean = false,
    val canSkip: Boolean = true,
    val error: String? = null,
)

/**
 * ViewModel for the multi-step onboarding flow.
 */
class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val pageOrder: List<OnboardingPage> = listOf(
        OnboardingPage.Welcome,
        OnboardingPage.Capabilities,
        OnboardingPage.Personalize,
        OnboardingPage.Notifications,
        OnboardingPage.Voice,
        OnboardingPage.AgeVerification,
        OnboardingPage.Complete,
    )

    fun nextPage() {
        val current = _uiState.value.currentPage
        val idx = pageOrder.indexOf(current)
        if (idx < pageOrder.size - 1) {
            _uiState.value = _uiState.value.copy(currentPage = pageOrder[idx + 1])
        }
    }

    fun previousPage() {
        val current = _uiState.value.currentPage
        val idx = pageOrder.indexOf(current)
        if (idx > 0) {
            _uiState.value = _uiState.value.copy(currentPage = pageOrder[idx - 1])
        }
    }

    fun skipOnboarding() {
        viewModelScope.launch {
            // TODO: mark onboarding complete in DataStore
            _uiState.value = _uiState.value.copy(currentPage = OnboardingPage.Complete)
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: persist onboarding completion flag
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
