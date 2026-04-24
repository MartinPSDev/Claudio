package com.anthropic.claude.chat.modelselector

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Server-provided model redirect instruction — tells the client to switch
 * from the requested model to a different one, with an explanation.
 */
@Serializable
data class ModelRedirect(
    @SerialName("destination_model_name") val destinationModelName: String,
    val explanation: String? = null,
    @SerialName("redirect_on") val redirectOn: String? = null,
)
