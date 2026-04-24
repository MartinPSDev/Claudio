package com.anthropic.claude.api.experience

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A tooltip displayed within the chat screen to surface tips or actions.
 */
@Serializable
data class ChatTooltipContent(
    val text: String? = null,
    @SerialName("action_type") val actionType: String? = null,
    @SerialName("location_id") val locationId: String? = null,
)
