package com.anthropic.claude.api.styles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The full styles configuration — bundles system defaults and user custom styles.
 */
@Serializable
data class StylesConfig(
    @SerialName("defaultStyles") val defaultStyles: List<DefaultStyle>? = null,
    @SerialName("customStyles") val customStyles: List<CustomStyle>? = null,
)
