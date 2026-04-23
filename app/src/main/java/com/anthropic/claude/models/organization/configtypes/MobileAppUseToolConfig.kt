package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Server-driven configuration for which mobile app-use tool integrations are enabled.
 * Each field maps to a versioned tool capability flag.
 */
@Serializable
data class MobileAppUseToolConfig(
    // Calendar tools
    @SerialName("calendar_search_v0") val calendarSearchV0: Boolean = false,
    @SerialName("event_create_v0") val eventCreateV0: Boolean = false,
    @SerialName("event_create_v1") val eventCreateV1: Boolean = false,
    @SerialName("event_search_v0") val eventSearchV0: Boolean = false,
    @SerialName("event_update_v0") val eventUpdateV0: Boolean = false,
    @SerialName("event_delete_v0") val eventDeleteV0: Boolean = false,
    // Alarms & timers
    @SerialName("alarm_create_v0") val alarmCreateV0: Boolean = false,
    @SerialName("timer_create_v0") val timerCreateV0: Boolean = false,
    // Messaging
    @SerialName("message_compose_v0") val messageComposeV0: Boolean = false,
    // Location & maps
    @SerialName("user_location_v0") val userLocationV0: Boolean = false,
    @SerialName("map_display_v0") val mapDisplayV0: Boolean = false,
    // Health
    @SerialName("health_connect_query_v0") val healthConnectQueryV0: Boolean = false,
    @SerialName("health_connect_data_types_v0") val healthConnectDataTypesV0: Boolean = false,
    // Utilities
    @SerialName("user_time_v0") val userTimeV0: Boolean = false,
    @SerialName("chart_display_v0") val chartDisplayV0: Boolean = false,
)
