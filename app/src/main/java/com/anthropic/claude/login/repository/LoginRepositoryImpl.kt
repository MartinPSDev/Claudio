package com.anthropic.claude.login.repository

import com.anthropic.claude.api.login.SendMagicLinkRequest
import com.anthropic.claude.api.login.SendMagicLinkResponse
import com.anthropic.claude.api.login.VerifyGoogleMobileRequest
import com.anthropic.claude.api.login.VerifyMagicLinkRequest
import com.anthropic.claude.api.login.VerifyResponse
import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.serialization.json.Json

/**
 * Concrete implementation of [LoginRepository].
 * Wraps [AnthropicApiClient] calls and maps HTTP responses to [ApiResult].
 */
class LoginRepositoryImpl(
    private val apiClient: AnthropicApiClient,
) : LoginRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    override suspend fun sendMagicLink(request: SendMagicLinkRequest): ApiResult<SendMagicLinkResponse> =
        execute { apiClient.sendMagicLink(request) }

    override suspend fun verifyMagicLink(request: VerifyMagicLinkRequest): ApiResult<VerifyResponse> =
        execute { apiClient.verifyMagicLink(request) }

    override suspend fun verifyGoogleMobile(request: VerifyGoogleMobileRequest): ApiResult<VerifyResponse> =
        execute { apiClient.verifyGoogleMobile(request) }

    override suspend fun verifySsoCallback(code: String, state: String): ApiResult<VerifyResponse> =
        execute { apiClient.verifySsoCallback(code, state) }

    private suspend inline fun <reified T> execute(
        crossinline call: suspend () -> okhttp3.Response,
    ): ApiResult<T> = try {
        val response = call()
        val bodyString = response.body?.string() ?: ""
        if (response.isSuccessful) {
            val parsed = json.decodeFromString<T>(bodyString)
            ApiResult.Success(parsed)
        } else {
            ApiResult.Error(response.code, bodyString.takeIf { it.isNotBlank() })
        }
    } catch (e: Exception) {
        ApiResult.NetworkError(e)
    }
}
