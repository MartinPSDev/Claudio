package com.anthropic.claude.login.viewmodel

import android.icu.util.TimeZone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.analytics.AnalyticsTracker
import com.anthropic.claude.analytics.events.LoginEvents
import com.anthropic.claude.api.login.SendMagicLinkRequest
import com.anthropic.claude.api.login.VerifyGoogleMobileRequest
import com.anthropic.claude.api.login.VerifyMagicLinkRequest
import com.anthropic.claude.api.login.VerifyResponse
import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.core.telemetry.SilentException
import com.anthropic.claude.datastore.SessionDataStore
import com.anthropic.claude.login.MagicLinkSentConfig
import com.anthropic.claude.login.ManagedLoginProvider
import com.anthropic.claude.login.repository.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val GOOGLE_ID_TOKEN_CREDENTIAL_TYPE =
    "com.google.android.libraries.identity.googleid.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL"

/** UI state for the login flow. */
data class LoginUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val magicLinkSent: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null,
)

sealed class LoginEvent {
    data class NavigateToSso(val email: String, val ssoUrl: String) : LoginEvent()
    data class MagicLinkSent(val config: MagicLinkSentConfig) : LoginEvent()
    data class AuthenticationSuccess(val accountId: String, val orgId: String) : LoginEvent()
    data class ShowManagedLoginError(val messageResId: Int) : LoginEvent()
}

/**
 * ViewModel for the login flow.
 */
class LoginViewModel(
    private val clientId: String = "com.anthropic.claude",
    private val loginRepository: LoginRepository,
    private val sessionDataStore: SessionDataStore,
    private val analyticsTracker: AnalyticsTracker,
    private val managedLoginProvider: ManagedLoginProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    fun sendMagicLink() {
        val email = _uiState.value.email.trim()
        if (email.isEmpty()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            analyticsTracker.trackEvent(
                LoginEvents.EmailLoginSendingMagicLink(),
                LoginEvents.EmailLoginSendingMagicLink.serializer()
            )

            val utcOffset = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 1000

            val request = SendMagicLinkRequest(
                email_address = email,
                recaptcha_token = null,
                recaptcha_site_key = null,
                utc_offset = utcOffset,
                source = null,
                client = null,
                login_intent = "magic_link"
            )

            when (val result = loginRepository.sendMagicLink(request)) {
                is ApiResult.Success -> {
                    val response = result.data
                    val ssoUrl = response.sso_url

                    if (ssoUrl != null) {
                        _events.emit(LoginEvent.NavigateToSso(email, ssoUrl))
                    } else {
                        analyticsTracker.trackEvent(
                            LoginEvents.EmailLoginMagicLinkSent(),
                            LoginEvents.EmailLoginMagicLinkSent.serializer()
                        )

                        val config = response.fallback_code_configuration
                        val sentConfig = MagicLinkSentConfig(
                            email = email,
                            codeLength = config?.length,
                            codeCharset = config?.charset,
                            showInputAfterDelay = config?.show_input_after_delay
                        )
                        _uiState.value = _uiState.value.copy(magicLinkSent = true)
                        _events.emit(LoginEvent.MagicLinkSent(sentConfig))
                    }
                }
                is ApiResult.Error -> {
                    analyticsTracker.trackEvent(
                        LoginEvents.EmailLoginMagicLinkSendError(),
                        LoginEvents.EmailLoginMagicLinkSendError.serializer()
                    )
                    _uiState.value = _uiState.value.copy(error = "Error sending magic link")
                }
                else -> {
                    _uiState.value = _uiState.value.copy(error = "Unknown error")
                }
            }

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    /**
     * Initiates Google Sign-In verification.
     * [credentialType] must equal [GOOGLE_ID_TOKEN_CREDENTIAL_TYPE] to proceed.
     * [idToken] is the token extracted from the Google credential bundle.
     */
    fun verifyGoogleSignIn(credentialType: String, idToken: String) {
        if (credentialType != GOOGLE_ID_TOKEN_CREDENTIAL_TYPE) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val request = VerifyGoogleMobileRequest(
                id_token = idToken,
                recaptcha_token = null,
                recaptcha_site_key = null,
                source = null,
                client = null,
                login_intent = null
            )

            when (val result = loginRepository.verifyGoogleMobile(request)) {
                is ApiResult.Success -> {
                    handleVerifyResponse(result.data)
                }
                is ApiResult.Error -> {
                    analyticsTracker.trackEvent(
                        LoginEvents.SignInWithGoogleError(),
                        LoginEvents.SignInWithGoogleError.serializer()
                    )
                    _uiState.value = _uiState.value.copy(error = "Google Sign In Error")
                }
                else -> {
                    _uiState.value = _uiState.value.copy(error = "Unknown error")
                }
            }

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun verifyMagicLink(nonce: String, encodedEmail: String = "") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val request = VerifyMagicLinkRequest(
                token = nonce,
                recaptcha_token = null,
                recaptcha_site_key = null,
                source = null,
                client = null,
                login_intent = null
            )

            when (val result = loginRepository.verifyMagicLink(request)) {
                is ApiResult.Success -> {
                    handleVerifyResponse(result.data)
                }
                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(error = "Invalid magic link")
                }
                else -> {
                    _uiState.value = _uiState.value.copy(error = "Unknown error")
                }
            }

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private suspend fun handleVerifyResponse(response: VerifyResponse) {
        val ssoUrl = response.sso_url
        if (ssoUrl != null) {
            _events.emit(LoginEvent.NavigateToSso(_uiState.value.email, ssoUrl))
            return
        }

        val authState = response.state
        val account = if (authState is VerifyResponse.AuthenticationState.Authenticated) {
            authState.account
        } else {
            response.account
        }

        if (account != null) {
            val orgId = account.organization?.uuid ?: ""
            val accountId = account.uuid_islZJdo ?: ""
            val email = account.email_address_ZiuLfiY ?: ""

            sessionDataStore.saveSession(
                accountUuid = accountId,
                orgUuid = orgId,
                displayEmail = email,
                bootstrapTs = System.currentTimeMillis().toString()
            )

            analyticsTracker.identify(accountId, orgId, email, null)
            _uiState.value = _uiState.value.copy(isAuthenticated = true)
            _events.emit(LoginEvent.AuthenticationSuccess(accountId, orgId))

        } else if (authState is VerifyResponse.AuthenticationState.MagicLink) {

            if (!managedLoginProvider.isManagedLoginAllowed()) {
                analyticsTracker.trackEvent(
                    LoginEvents.ManagedLoginBlocked("magic_link"),
                    LoginEvents.ManagedLoginBlocked.serializer()
                )
                _events.emit(LoginEvent.ShowManagedLoginError(com.anthropic.claude.R.string.managed_login_blocked_message))
                return
            }

            val config = authState.fallback_code_configuration
            val sentConfig = MagicLinkSentConfig(
                email = authState.email_ZiuLfiY ?: "",
                codeLength = config?.length,
                codeCharset = config?.charset,
                showInputAfterDelay = config?.show_input_after_delay
            )
            _uiState.value = _uiState.value.copy(magicLinkSent = true)
            _events.emit(LoginEvent.MagicLinkSent(sentConfig))

        } else {
            SilentException("VerifyResponse Error: Cannot find account").report()
            _uiState.value = _uiState.value.copy(error = "Authentication failed")
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetMagicLinkState() {
        _uiState.value = _uiState.value.copy(magicLinkSent = false)
    }
}
