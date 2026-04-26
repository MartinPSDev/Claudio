package com.anthropic.claude.repository

import com.anthropic.claude.api.account.Account
import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

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
    private val json = Json { ignoreUnknownKeys = true }

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    /**
     * Validates the existing session cookie against /api/account.
     * Called at app launch to determine if the user is already authenticated.
     */
    suspend fun bootstrap(): ApiResult<Account> {
        return try {
            val response = apiClient.getAccount()
            val bodyString = response.body?.string() ?: ""
            if (response.isSuccessful) {
                val account = json.decodeFromString<Account>(bodyString)
                _sessionState.value = SessionState.LoggedIn(account)
                ApiResult.Success(account)
            } else if (response.code == 401 || response.code == 403) {
                _sessionState.value = SessionState.LoggedOut
                ApiResult.Error(response.code, "Session expired")
            } else {
                ApiResult.Error(response.code, bodyString.takeIf { it.isNotBlank() })
            }
        } catch (e: Exception) {
            _sessionState.value = SessionState.LoggedOut
            ApiResult.NetworkError(e)
        }
    }

    /** Called by AuthExpiredInterceptor when a 401 is received mid-session. */
    fun onAuthExpired() {
        _sessionState.value = SessionState.AuthExpired
    }

    /** Called after magic-link / Google verification succeeds. */
    fun onLoginSuccess(account: Account) {
        _sessionState.value = SessionState.LoggedIn(account)
    }

    suspend fun logout(): ApiResult<Unit> {
        return try {
            val response = apiClient.logout()
            _sessionState.value = SessionState.LoggedOut
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            _sessionState.value = SessionState.LoggedOut
            ApiResult.NetworkError(e)
        }
    }

    fun currentAccount(): Account? =
        (_sessionState.value as? SessionState.LoggedIn)?.account
}
