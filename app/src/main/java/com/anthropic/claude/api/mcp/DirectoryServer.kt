package com.anthropic.claude.api.mcp

import com.anthropic.claude.types.strings.DirectoryServerId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectoryServer(
    val id: DirectoryServerId,
    val type: DirectoryServerType,
    val name: String? = null,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("one_liner") val oneLiner: String? = null,
    val description: String? = null,
    @SerialName("icon_url") val iconUrl: String? = null,
    val categories: List<String> = emptyList(),
    @SerialName("tool_names") val toolNames: List<String> = emptyList(),
    @SerialName("has_mcp_app") val hasMcpApp: Boolean? = null,
    val rank: Int? = null,
    val trending: Boolean? = null,
    @SerialName("added_at") val addedAt: String? = null,
    val author: Author? = null,
    val documentation: String? = null,
    val support: String? = null,
    @SerialName("privacy_policy") val privacyPolicy: String? = null,
    @SerialName("image_urls") val imageUrls: List<PromptImage> = emptyList(),
    val remote: RemoteDetails? = null,
) {
    @Serializable
    data class Author(
        val name: String? = null,
        val url: String? = null,
    )

    @Serializable
    data class PromptImage(
        val prompt: String? = null,
        @SerialName("image_url") val imageUrl: String? = null,
    )

    @Serializable
    data class RemoteDetails(
        val url: String? = null,
        @SerialName("is_authless") val isAuthless: Boolean = false,
    )
}
