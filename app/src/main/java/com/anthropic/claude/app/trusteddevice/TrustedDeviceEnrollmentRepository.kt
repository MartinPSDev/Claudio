package com.anthropic.claude.app.trusteddevice

import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.api.trusteddevice.EnrollTrustedDeviceResponse
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.serialization.json.Json

/**
 * Network-layer repository for trusted device enrollment.
 * Calls the POST /api/auth/trusted_device/enroll endpoint.
 */
class TrustedDeviceEnrollmentRepository(
    private val apiClient: AnthropicApiClient,
) {

    /**
     * Enrolls the current device as trusted.
     *
     * @param deviceName Human-readable name (e.g. "Samsung Galaxy S24 Ultra").
     * @return [ApiResult] wrapping the enrollment response.
     */
    suspend fun enroll(deviceName: String): ApiResult<EnrollTrustedDeviceResponse> {
        return try {
            val response = apiClient.enrollTrustedDevice(deviceName)
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""
                val parsed = Json.decodeFromString<EnrollTrustedDeviceResponse>(body)
                ApiResult.Success(parsed)
            } else {
                val errorBody = response.body?.string() ?: ""
                ApiResult.Error(
                    statusCode = response.code,
                    errorCode = parseErrorCode(errorBody),
                    message = errorBody,
                )
            }
        } catch (e: Exception) {
            ApiResult.NetworkError(e)
        }
    }

    private fun parseErrorCode(body: String): String? {
        return try {
            val json = Json.parseToJsonElement(body)
            json.jsonObject["error"]?.jsonObject?.get("type")?.toString()?.trim('"')
        } catch (_: Exception) {
            null
        }
    }
}
