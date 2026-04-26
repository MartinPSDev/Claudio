package com.anthropic.claude.app.trusteddevice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.api.result.ApiResult
import com.anthropic.claude.api.trusteddevice.EnrollTrustedDeviceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Trusted Device enrollment flow.
 *
 * On success the server returns an [EnrollTrustedDeviceResponse] whose
 * [device_token] field (or the __Host-ant_trusted_device cookie) is saved
 * by [TrustedDeviceStore]. On failure, a typed [EnrollmentError] is exposed.
 */
class TrustedDeviceEnrollmentViewModel(
    private val enrollmentRepository: TrustedDeviceEnrollmentRepository,
    private val trustedDeviceStore: TrustedDeviceStore,
    private val cookieJar: CookieJar,
    private val logger: AppLogger,
) : ViewModel() {

    /** Name of the cookie set by the server to store the trusted device token. */
    private val TRUSTED_DEVICE_COOKIE_NAME = "__Host-ant_trusted_device"

    // ── Enrollment state ──────────────────────────────────────────────────────

    /** True while an enrollment request is in-flight. */
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /** Set to a [Throwable] if enrollment fails with an unexpected error. */
    private val _enrollmentError = MutableStateFlow<Throwable?>(null)
    val enrollmentError: StateFlow<Throwable?> = _enrollmentError.asStateFlow()

    /** Typed enrollment error (e.g. session too old, device limit reached). */
    private val _typedError = MutableStateFlow<EnrollmentError?>(null)
    val typedError: StateFlow<EnrollmentError?> = _typedError.asStateFlow()

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Initiates trusted device enrollment.
     * Builds the device name from [Build.MANUFACTURER] + [Build.MODEL] (trimmed).
     *
     * On success, saves the device token from the response body or, as a
     * fallback, extracts it from the __Host-ant_trusted_device cookie.
     */
    fun enroll() {
        viewModelScope.launch {
            _isLoading.value = false
            _enrollmentError.value = null

            val deviceName = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}".trim()

            when (val result = enrollmentRepository.enroll(deviceName)) {
                is ApiResult.Success -> handleSuccess(result.value)
                is ApiResult.Error   -> handleApiError(result)
                is ApiResult.NetworkError -> {
                    _enrollmentError.value = Exception("Network error during enrollment")
                    setLoading(false)
                }
            }
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private fun handleSuccess(response: EnrollTrustedDeviceResponse) {
        val token = response.deviceToken
            ?: cookieJar.findCookieValue(TRUSTED_DEVICE_COOKIE_NAME)

        if (token != null) {
            trustedDeviceStore.saveToken(token)
            logger.info("Trusted device enrolled")
            setLoading(true)
        } else {
            // Server returned 2xx but no token — treat as failure
            setLoading(true)
            _enrollmentError.value = IllegalStateException("Enrollment returned no token")
            logger.warning("Enrollment succeeded but no token in body or $TRUSTED_DEVICE_COOKIE_NAME cookie")
        }
    }

    private fun handleApiError(error: ApiResult.Error) {
        when (error.errorCode) {
            "session_too_old_for_enrollment" -> {
                setLoading(true)
                _typedError.value = EnrollmentError.SESSION_TOO_OLD
            }
            "trusted_device_limit_reached" -> {
                setLoading(true)
                _typedError.value = EnrollmentError.DEVICE_LIMIT_REACHED
            }
            else -> {
                _enrollmentError.value = Exception(error.message ?: error.toString())
                logger.error("Enrollment failed: $error")
            }
        }
    }

    /**
     * Drives [_isLoading] — mirrors the `b(Z)V` method from the original.
     */
    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}

/**
 * Typed enrollment error codes returned by the server.
 */
enum class EnrollmentError {
    /** The session is too old and must be refreshed before enrolling. */
    SESSION_TOO_OLD,

    /** The account has reached the maximum number of trusted devices. */
    DEVICE_LIMIT_REACHED,
}
