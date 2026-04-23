package com.anthropic.claude.analytics.ads

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Install attribution data from the Google Play Referrer API.
 * Captured at first launch to attribute installs to ad campaigns.
 */
@Serializable
data class GooglePlayReferrer(
    @SerialName("install_referrer") val installReferrer: String,
    @SerialName("referrer_click_timestamp_seconds") val referrerClickTimestampSeconds: Long = 0L,
    @SerialName("install_begin_timestamp_seconds") val installBeginTimestampSeconds: Long = 0L,
    @SerialName("referrer_click_timestamp_server_seconds") val referrerClickTimestampServerSeconds: Long = 0L,
    @SerialName("install_begin_timestamp_server_seconds") val installBeginTimestampServerSeconds: Long = 0L,
)
