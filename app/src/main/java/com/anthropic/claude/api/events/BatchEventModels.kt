package com.anthropic.claude.api.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Authentication context embedded in batch event logs. */
@Serializable
data class EventAuth(
    @SerialName("account_uuid")      val accountUuid: String? = null,
    @SerialName("organization_uuid") val organizationUuid: String? = null,
)

/** Container for a batch of analytics events. */
@Serializable
data class BatchEventLoggingRequest(val events: JsonElement? = null)
