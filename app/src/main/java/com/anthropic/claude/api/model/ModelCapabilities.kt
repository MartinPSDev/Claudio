package com.anthropic.claude.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Set of capabilities supported by a given model.
 */
@Serializable
data class ModelCapabilities(
    @SerialName("mm_pdf") val mmPdf: Boolean = false,
    @SerialName("mm_images") val mmImages: Boolean = false,
    @SerialName("web_search") val webSearch: Boolean = false,
    val compass: Boolean = false,
    @SerialName("gsuite_tools") val gsuiteTools: Boolean = false,
)
