package com.anthropic.claude.login.repository

import com.anthropic.claude.api.login.SendMagicLinkRequest
import com.anthropic.claude.api.login.SendMagicLinkResponse
import com.anthropic.claude.api.login.VerifyGoogleMobileRequest
import com.anthropic.claude.api.login.VerifyMagicLinkRequest
import com.anthropic.claude.api.login.VerifyResponse
import com.anthropic.claude.api.result.ApiResult

/**
 * Provides access to login networking calls.
 */
interface LoginRepository {
    suspend fun sendMagicLink(request: SendMagicLinkRequest): ApiResult<SendMagicLinkResponse>
    suspend fun verifyMagicLink(request: VerifyMagicLinkRequest): ApiResult<VerifyResponse>
    suspend fun verifyGoogleMobile(request: VerifyGoogleMobileRequest): ApiResult<VerifyResponse>
}
