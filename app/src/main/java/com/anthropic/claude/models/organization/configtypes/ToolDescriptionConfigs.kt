package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Config for sticky model/style selection behavior. */
@Serializable
data class StickySelectionConfig(
    @SerialName("model_selector") val modelSelector: JsonElement? = null,
)

/** A supported STT (speech-to-text) language entry. */
@Serializable
data class SttSupportedLanguage(
    val code: String? = null,
    @SerialName("display_name") val displayName: String? = null,
)

/** Descriptions for the message-compose tool inputs. */
@Serializable
data class MessageComposeInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val body: String? = null,
    val kind: String? = null,
    val subject: String? = null,
    @SerialName("summary_title") val summaryTitle: String? = null,
)

/** Descriptions for the timer-create tool inputs. */
@Serializable
data class TimerCreateInputDescriptions(
    @SerialName("tool_description")   val toolDescription: String? = null,
    @SerialName("duration_seconds")   val durationSeconds: String? = null,
    val message: String? = null,
)

/** Descriptions for the user-location tool inputs. */
@Serializable
data class UserLocationInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val accuracy: String? = null,
)

/** Descriptions for the user-time tool. */
@Serializable
data class UserTimeToolDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
)

/** Descriptions for the map-display tool inputs. */
@Serializable
data class MapDisplayInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val title: String? = null,
    val markers: String? = null,
)

/** Per-marker description for map-display. */
@Serializable
data class MapDisplayMarkersItemDescription(
    val description: String? = null,
    val title: String? = null,
    val label: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
)

/** Array wrapper description for map-display markers. */
@Serializable
data class MapDisplayMarkersArrayDescription(
    val description: String? = null,
    val items: String? = null,
)

/** Descriptions for the HealthConnect query tool inputs. */
@Serializable
data class HealthConnectQueryInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    val queries: String? = null,
)

/** Descriptions for the HealthConnect data-types tool inputs. */
@Serializable
data class HealthConnectDataTypesInputDescriptions(
    @SerialName("tool_description") val toolDescription: String? = null,
    @SerialName("search_text") val searchText: String? = null,
)
