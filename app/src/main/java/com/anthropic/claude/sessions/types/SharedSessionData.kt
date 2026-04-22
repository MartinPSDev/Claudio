package com.anthropic.claude.sessions.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Paginated response containing shared session events and the associated session resource.
 */
@Serializable
data class SharedSessionData(
    val data: List<SdkEvent> = emptyList(),
    val session: SessionResource? = null,
    @SerialName("sharer_display_name") val sharerDisplayName: String? = null,
    val type: String? = null,
    @SerialName("has_more") val hasMore: Boolean = false,
    @SerialName("first_id") val firstId: String? = null,
    @SerialName("last_id") val lastId: String? = null,
)

/** Marker interface for SDK-level session events. */
@Serializable
data class SdkEvent(
    val type: String? = null,
    val data: String? = null,
)

/** Top-level resource representing a session on the server. */
@Serializable
data class SessionResource(
    val id: String? = null,
    val type: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    val name: String? = null,
    val summary: String? = null,
    val model: String? = null,
    val context: SessionContext? = null,
)
