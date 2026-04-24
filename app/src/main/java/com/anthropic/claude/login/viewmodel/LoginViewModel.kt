package com.anthropic.claude.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.login.SendMagicLinkRequest
import com.anthropic.claude.api.login.VerifyMagicLinkRequest
import com.anthropic.claude.api.login.VerifyGoogleMobileRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the login flow. */
data class LoginUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val magicLinkSent: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the login flow.
 * Handles magic-link sending, verification, and Google SSO.
 */
class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    fun sendMagicLink() {
        val email = _uiState.value.email.trim()
        if (email.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.sendMagicLink(SendMagicLinkRequest(email = email))
            _uiState.value = _uiState.value.copy(isLoading = false, magicLinkSent = true)
        }
    }

    fun verifyMagicLink(nonce: String, encodedEmail: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.verifyMagicLink(VerifyMagicLinkRequest(credentials = ...))
            _uiState.value = _uiState.value.copy(isLoading = false, isAuthenticated = true)
        }
    }

    fun verifyGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.verifyGoogleMobile(VerifyGoogleMobileRequest(token = idToken))
            _uiState.value = _uiState.value.copy(isLoading = false, isAuthenticated = true)
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetMagicLinkState() {
        _uiState.value = _uiState.value.copy(magicLinkSent = false)
    }
}
