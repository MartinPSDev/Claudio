package com.anthropic.claude.app.trusteddevice

import android.util.Log
import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.api.trusteddevice.EnrollTrustedDeviceResponse
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Network-layer repository for trusted device enrollment.
 * Calls the POST /api/auth/trusted_device/enroll endpoint.
 *
 * On success, the server returns the token either in the response body
 * or as a `__Host-ant_trusted_device` Set-Cookie header. The token
 * is persisted to [TrustedDeviceStore] for future authentication.
 *
 * Known error codes:
 *   - `session_too_old_for_enrollment` — session must be refreshed first
 *   - `trusted_device_limit_reached` — max devices enrolled for this account
 */
class TrustedDeviceEnrollmentRepository(
    private val apiClient: AnthropicApiClient,
    private val trustedDeviceStore: TrustedDeviceStore? = null,
) {
    companion object {
        private const val TAG = "TrustedDeviceEnrollment"
        private const val TRUSTED_DEVICE_COOKIE = "__Host-ant_trusted_device"
        const val ERROR_SESSION_TOO_OLD = "session_too_old_for_enrollment"
        const val ERROR_LIMIT_REACHED = "trusted_device_limit_reached"
    }

    private val json = Json { ignoreUnknownKeys = true }

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
                val parsed = json.decodeFromString<EnrollTrustedDeviceResponse>(body)

                // Extract token: prefer body token, fall back to Set-Cookie header
                val token = parsed.token ?: extractTrustedDeviceCookie(response)

                if (token != null) {
                    trustedDeviceStore?.saveToken(token)
                    Log.i(TAG, "Trusted device enrolled")
                    ApiResult.Success(parsed.copy(token = token))
                } else {
                    Log.w(TAG, "Enrollment succeeded but no token in body or $TRUSTED_DEVICE_COOKIE cookie")
                    ApiResult.Success(parsed)
                }
            } else {
                val errorBody = response.body?.string() ?: ""
                val errorCode = parseErrorCode(errorBody)

                when (errorCode) {
                    ERROR_SESSION_TOO_OLD -> {
                        Log.w(TAG, "Enrollment failed: session too old")
                    }
                    ERROR_LIMIT_REACHED -> {
                        Log.w(TAG, "Enrollment failed: device limit reached")
                    }
                    else -> {
                        Log.e(TAG, "Enrollment failed: $errorBody")
                    }
                }

                ApiResult.Error(
                    statusCode = response.code,
                    errorCode = errorCode,
                    message = errorBody,
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Enrollment network error", e)
            ApiResult.NetworkError(e)
        }
    }

    /**
     * Extracts the `__Host-ant_trusted_device` token from Set-Cookie response headers.
     */
    private fun extractTrustedDeviceCookie(response: okhttp3.Response): String? {
        val setCookieHeaders = response.headers("Set-Cookie")
        for (header in setCookieHeaders) {
            if (header.startsWith(TRUSTED_DEVICE_COOKIE)) {
                val value = header
                    .substringAfter("=")
                    .substringBefore(";")
                    .trim()
                if (value.isNotEmpty()) return value
            }
        }
        return null
    }

    private fun parseErrorCode(body: String): String? {
        return try {
            val element = json.parseToJsonElement(body)
            element.jsonObject["error"]?.jsonObject?.get("type")?.jsonPrimitive?.content
        } catch (_: Exception) {
            null
        }
    }
}

