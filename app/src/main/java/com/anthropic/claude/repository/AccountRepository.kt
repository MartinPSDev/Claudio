package com.anthropic.claude.repository

import com.anthropic.claude.api.account.Account
import com.anthropic.claude.api.account.Organization
import com.anthropic.claude.api.account.UpdateAccountRequest
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository for account and organization data.
 * Holds an in-memory cache of the current account and org.
 */
class AccountRepository(
    private val apiClient: AnthropicApiClient,
) {
    private val _account = MutableStateFlow<Account?>(null)
    val account: StateFlow<Account?> = _account.asStateFlow()

    private val _organization = MutableStateFlow<Organization?>(null)
    val organization: StateFlow<Organization?> = _organization.asStateFlow()

    suspend fun refreshAccount(): ApiResult<Account> {
        // TODO: val result = apiClient.getAccount()
        // if (result is ApiResult.Success) _account.value = result.data
        // return result
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun updateAccount(request: UpdateAccountRequest): ApiResult<Account> {
        // TODO: val result = apiClient.updateAccount(request)
        // if (result is ApiResult.Success) _account.value = result.data
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun refreshOrganization(orgId: String): ApiResult<Organization> {
        // TODO: val result = apiClient.getOrganization(orgId)
        // if (result is ApiResult.Success) _organization.value = result.data
        return ApiResult.Error(501, "Not implemented")
    }

    fun clearSession() {
        _account.value = null
        _organization.value = null
    }
}
