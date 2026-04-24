package com.anthropic.claude.analytics.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Error type for age-signals API failures. */
@Serializable
enum class AgeSignalsErrorType {
    @SerialName("UNSPECIFIED")   UNSPECIFIED,
    @SerialName("retryable")     RETRYABLE,
    @SerialName("NON_RETRYABLE") NON_RETRYABLE,
}

/** App cold/warm start type for launch analytics. */
@Serializable
enum class AppLaunchStartType {
    @SerialName("unspecified") UNSPECIFIED,
    @SerialName("COLD")        COLD,
    @SerialName("WARM")        WARM,
}

/** Failure cause for push notification registration events. */
@Serializable
enum class PushFailureCause {
    @SerialName("UNSPECIFIED")       UNSPECIFIED,
    @SerialName("fcm_token_failed")  FCM_TOKEN_FAILED,
    @SerialName("network_error")     NETWORK_ERROR,
}
