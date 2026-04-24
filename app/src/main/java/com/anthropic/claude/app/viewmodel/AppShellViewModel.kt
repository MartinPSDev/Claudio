package com.anthropic.claude.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.account.Account
import com.anthropic.claude.api.account.Organization
import com.anthropic.claude.app.main.MainAppScreens
import com.anthropic.claude.app.main.loggedin.BootstrapScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Root UI state for the app shell. */
data class AppShellUiState(
    val screen: MainAppScreens = MainAppScreens.LoggedOut,
    val bootstrapScreen: BootstrapScreen = BootstrapScreen.NeedsBootstrap,
    val account: Account? = null,
    val organization: Organization? = null,
    val isInitializing: Boolean = true,
)

/**
 * Root ViewModel for the app shell / navigation host.
 * Bootstraps session state and drives top-level navigation.
 */
class AppShellViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppShellUiState())
    val uiState: StateFlow<AppShellUiState> = _uiState.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            // TODO: check for saved session cookie via NetworkingModule
            // If valid → bootstrap; if not → show LoggedOut
            _uiState.value = _uiState.value.copy(
                screen = MainAppScreens.LoggedOut,
                isInitializing = false,
            )
        }
    }

    fun onBootstrapSuccess(account: Account, organization: Organization) {
        _uiState.value = _uiState.value.copy(
            screen = MainAppScreens.LoggedIn,
            bootstrapScreen = BootstrapScreen.Bootstrapped(
                accountId = account.uuid ?: "",
                organizationId = organization.uuid ?: "",
            ),
            account = account,
            organization = organization,
            isInitializing = false,
        )
    }

    fun onLoggedOut() {
        _uiState.value = AppShellUiState(
            screen = MainAppScreens.LoggedOut,
            isInitializing = false,
        )
    }

    fun onAuthExpired() {
        onLoggedOut()
    }

    fun switchOrganization(organizationId: String) {
        viewModelScope.launch {
            // TODO: apiClient.switchOrganization(organizationId) then re-bootstrap
        }
    }
}
