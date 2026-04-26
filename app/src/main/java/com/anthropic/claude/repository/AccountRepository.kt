package com.anthropic.claude.repository

import com.anthropic.claude.api.account.Account
import com.anthropic.claude.api.account.Organization
import com.anthropic.claude.api.account.UpdateAccountRequest
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

/**
 * Repository for account and organization data.
 * Holds an in-memory cache of the current account and org.
 */
class AccountRepository(
    private val apiClient: AnthropicApiClient,
) {
    private val json = Json { ignoreUnknownKeys = true }

    private val _account = MutableStateFlow<Account?>(null)
    val account: StateFlow<Account?> = _account.asStateFlow()

    private val _organization = MutableStateFlow<Organization?>(null)
    val organization: StateFlow<Organization?> = _organization.asStateFlow()

    suspend fun refreshAccount(): ApiResult<Account> = execute {
        val response = apiClient.getAccount()
        response to response.body?.string()
    } { body ->
        val account = json.decodeFromString<Account>(body)
        _account.value = account
        account
    }

    suspend fun updateAccount(request: UpdateAccountRequest): ApiResult<Account> = execute {
        val response = apiClient.updateAccount(request)
        response to response.body?.string()
    } { body ->
        val account = json.decodeFromString<Account>(body)
        _account.value = account
        account
    }

    fun clearSession() {
        _account.value = null
        _organization.value = null
    }

    private suspend inline fun <reified T> execute(
        crossinline call: suspend () -> Pair<okhttp3.Response, String?>,
        crossinline map: (String) -> T,
    ): ApiResult<T> = try {
        val (response, body) = call()
        val bodyStr = body ?: ""
        if (response.isSuccessful) {
            ApiResult.Success(map(bodyStr))
        } else {
            ApiResult.Error(response.code, bodyStr.takeIf { it.isNotBlank() })
        }
    } catch (e: Exception) {
        ApiResult.NetworkError
    }
}
