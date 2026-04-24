package com.anthropic.claude.repository

import com.anthropic.claude.api.account.Account
import com.anthropic.claude.api.sync.AuthStatus
import com.anthropic.claude.api.sync.FinishAuthRequest
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Authentication session states. */
sealed interface SessionState {
    data object Loading : SessionState
    data object LoggedOut : SessionState
    data class LoggedIn(val account: Account) : SessionState
    data object AuthExpired : SessionState
}

/**
 * Repository managing authentication session lifecycle.
 * Bridges cookie-based auth with SSO / magic-link verification flows.
 */
class SessionRepository(
    private val apiClient: AnthropicApiClient,
) {
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    /** Bootstrap — validate existing session cookie against the server. */
    suspend fun bootstrap(): ApiResult<Account> {
        // TODO: apiClient.bootstrapSession()
        //   on success → _sessionState.value = SessionState.LoggedIn(account)
        //   on 401     → _sessionState.value = SessionState.LoggedOut
        return ApiResult.Error(501, "Not implemented")
    }

    /** Called by AuthExpiredInterceptor when a 401 is received. */
    fun onAuthExpired() {
        _sessionState.value = SessionState.AuthExpired
    }

    /** Finish SSO auth handshake after receiving the callback intent. */
    suspend fun finishSsoAuth(request: FinishAuthRequest): ApiResult<Account> {
        // TODO: apiClient.finishAuth(request) → if success bootstrap
        return ApiResult.Error(501, "Not implemented")
    }

    /** Called after magic-link / Google verification succeeds. */
    fun onLoginSuccess(account: Account) {
        _sessionState.value = SessionState.LoggedIn(account)
    }

    fun logout() {
        _sessionState.value = SessionState.LoggedOut
        // TODO: clear cookies via CookieJar
    }

    fun currentAccount(): Account? =
        (_sessionState.value as? SessionState.LoggedIn)?.account
}
