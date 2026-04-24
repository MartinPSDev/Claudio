package com.anthropic.claude.analytics.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── Age Signals Events ────────────────────────────────────────────────────────

object AgeSignalsEvents {

    enum class AgeSignalsErrorType { UNSPECIFIED, RETRYABLE, NON_RETRYABLE }

    enum class AgeSignalsResult { UNSPECIFIED, DECLINED_SHARING, ERROR, RETRYABLE_ERROR, SUPERVISED }

    @Serializable
    data class AgeSignalsCheckStarted(
        @SerialName("is_debug_override") val isDebugOverride: Boolean? = null,
    ) {
        companion object { const val EVENT_NAME = "mobile.age_signals.check_started" }
    }

    @Serializable
    data class AgeSignalsCheckCompleted(
        val result: AgeSignalsResult? = null,
        @SerialName("attempt_count")      val attemptCount: Int? = null,
        @SerialName("duration_ms")        val durationMs: Long? = null,
        @SerialName("is_debug_override")  val isDebugOverride: Boolean? = null,
    ) {
        companion object { const val EVENT_NAME = "mobile.age_signals.check_completed" }
    }

    @Serializable
    data class AgeSignalsApiError(
        @SerialName("error_code")      val errorCode: String? = null,
        @SerialName("error_type")      val errorType: AgeSignalsErrorType? = null,
        @SerialName("attempt_number")  val attemptNumber: Int? = null,
    ) {
        companion object { const val EVENT_NAME = "mobile.age_signals.api_error" }
    }
}

// ── App Start Events ──────────────────────────────────────────────────────────

object AppStartEvents {

    enum class AppLaunchStartType { UNSPECIFIED, COLD, WARM }

    @Serializable
    data class AppLaunchCompleted(
        @SerialName("start_type")       val startType: AppLaunchStartType? = null,
        @SerialName("duration_ms")      val durationMs: Long? = null,
        @SerialName("app_on_create_ms") val appOnCreateMs: Long? = null,
    ) {
        companion object { const val EVENT_NAME = "mobile.app_start.launch_completed" }
    }

    @Serializable
    data object BootstrapValidCookie {
        const val EVENT_NAME = "mobile.app_start.bootstrap.valid_cookie"
    }

    @Serializable
    data class MaybeRefreshRefreshed(
        @SerialName("organization_uuid") val organizationUuid: String? = null,
    ) {
        companion object { const val EVENT_NAME = "claudeai.app_start.maybe_refresh.refreshed" }
    }
}

// ── Networking / Cronet Analytics Events ─────────────────────────────────────

object CronetAnalyticsEvents {

    @Serializable
    data class InitializationFailed(
        @SerialName("failure_reason") val failureReason: String? = null,
        @SerialName("error_message")  val errorMessage: String? = null,
    ) {
        companion object { const val EVENT_NAME = "claudeai.networking.cronet.initialization_failed" }
    }

    @Serializable
    data class RequestSuccess(
        val protocol: String? = null,
        @SerialName("status_code")          val statusCode: Int? = null,
        @SerialName("duration_ms")          val durationMs: Long? = null,
        @SerialName("experiment_enabled")   val experimentEnabled: Boolean? = null,
    ) {
        companion object { const val EVENT_NAME = "claudeai.networking.cronet.request_success" }
    }
}

object NetworkingEvents {

    @Serializable
    data class RequestSuccess(
        val endpoint: String? = null,
        @SerialName("status_code") val statusCode: Int? = null,
        @SerialName("duration_ms") val durationMs: Long? = null,
    ) {
        companion object { const val EVENT_NAME = "mobile.networking.request_success" }
    }
}
