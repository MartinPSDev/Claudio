package com.anthropic.claude.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.account.Account
import com.anthropic.claude.api.account.AccountSettings
import com.anthropic.claude.api.account.UpdateAccountRequest
import com.anthropic.claude.api.notification.NotificationPreferencesUpdateParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** UI state for the settings screen. */
data class SettingsUiState(
    val account: Account? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for the settings screen.
 * Handles account updates, notification preferences, and account deletion.
 */
class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun loadAccount() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // TODO: apiClient.getAccount()
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun updateDisplayName(displayName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            // TODO: apiClient.updateAccount(UpdateAccountRequest(displayName = displayName))
            _uiState.value = _uiState.value.copy(isSaving = false)
        }
    }

    fun updateNotificationPreferences(params: NotificationPreferencesUpdateParams) {
        viewModelScope.launch {
            // TODO: apiClient.updateNotificationPreferences(params)
        }
    }

    fun requestAccountDeletion() {
        viewModelScope.launch {
            // TODO: apiClient.requestAccountDeletion()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            // TODO: clear session cookies and navigate to login
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
