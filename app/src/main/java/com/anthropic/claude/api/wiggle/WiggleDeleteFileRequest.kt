package com.anthropic.claude.api.wiggle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request to delete a Wiggle uploaded file. */
@Serializable
data class WiggleDeleteFileRequest(
    @SerialName("file_uuid") val fileUuid: String? = null,
)
