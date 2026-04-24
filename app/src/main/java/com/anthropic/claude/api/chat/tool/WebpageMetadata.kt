package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Metadata about a webpage referenced in a tool result.
 */
@Serializable
data class WebpageMetadata(
    @SerialName("favicon_url") val faviconUrl: String? = null,
    @SerialName("site_name") val siteName: String? = null,
    @SerialName("site_domain") val siteDomain: String? = null,
)
