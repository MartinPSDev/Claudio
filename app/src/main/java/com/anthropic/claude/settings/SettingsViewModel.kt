package com.anthropic.claude.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.datastore.UserPreferencesDataStore
import com.anthropic.claude.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Settings screen.
 *
 * Manages user preferences (theme, haptics, notifications),
 * account information display, and logout flow.
 */
class SettingsViewModel(
    private val accountRepository: AccountRepository,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val orgId: String,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val account = accountRepository.getCurrentAccount(orgId)
                _state.value = _state.value.copy(
                    displayName = account?.fullName,
                    email = account?.emailAddress,
                    subscriptionTier = account?.subscriptionLevel ?: "free",
                    isLoading = false,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        _state.value = _state.value.copy(themeMode = mode)
        viewModelScope.launch {
            userPreferencesDataStore.setThemeMode(mode.name)
        }
    }

    fun setHapticsEnabled(enabled: Boolean) {
        _state.value = _state.value.copy(hapticsEnabled = enabled)
        viewModelScope.launch {
            userPreferencesDataStore.setHapticsEnabled(enabled)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        _state.value = _state.value.copy(notificationsEnabled = enabled)
    }

    fun logout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoggingOut = true)
            try {
                accountRepository.logout(orgId)
                _state.value = _state.value.copy(isLoggedOut = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoggingOut = false,
                    error = "Logout failed: ${e.message}",
                )
            }
        }
    }

    fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
}

// ── Models ───────────────────────────────────────────────────────────────

data class SettingsUiState(
    val displayName: String? = null,
    val email: String? = null,
    val subscriptionTier: String = "free",
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val hapticsEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val isLoading: Boolean = true,
    val isLoggingOut: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String? = null,
)

enum class ThemeMode {
    LIGHT, DARK, SYSTEM,
}
