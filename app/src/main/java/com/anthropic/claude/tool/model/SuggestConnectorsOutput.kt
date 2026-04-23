package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A connector suggestion item returned by the suggest-connectors tool.
 */
@Serializable
data class SuggestConnectorsOutputConnectorsItem(
    val description: String? = null,
    val name: String? = null,
    val url: String? = null,
    @SerialName("iconUrl") val iconUrl: String? = null,
    @SerialName("directoryUuid") val directoryUuid: String? = null,
    @SerialName("installedServerId") val installedServerId: String? = null,
    @SerialName("installState") val installState: String? = null,
    @SerialName("isAuthless") val isAuthless: Boolean = false,
)
