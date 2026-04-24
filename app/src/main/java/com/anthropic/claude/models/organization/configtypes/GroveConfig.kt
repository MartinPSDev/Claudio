package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Server-driven configuration for the Grove (Projects) feature.
 */
@Serializable
data class GroveConfig(
    @SerialName("notice_is_grace_period") val noticeIsGracePeriod: Boolean = false,
    @SerialName("notice_reminder_frequency") val noticeReminderFrequency: Int? = null,
    @SerialName("domain_excluded") val domainExcluded: Boolean = false,
    @SerialName("mobile_strings") val mobileStrings: JsonElement? = null,
)
