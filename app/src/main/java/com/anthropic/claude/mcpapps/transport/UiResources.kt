package com.anthropic.claude.mcpapps.transport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Result content returned by a tool call invocation in the MCP App protocol.
 */
@Serializable
data class ToolResultParams(
    @SerialName("tool_use_id") val toolUseId: String,
    val content: List<ResourceContent> = emptyList(),
    @SerialName("is_error") val isError: Boolean = false,
)

/**
 * A single content item returned by a resource or tool result.
 */
@Serializable
data class ResourceContent(
    val uri: String? = null,
    val mimeType: String? = null,
    val text: String? = null,
    @SerialName("_meta") val meta: ResourceMeta? = null,
)

/** Optional metadata attached to a resource content item. */
@Serializable
data class ResourceMeta(
    val cacheControl: String? = null,
)

/**
 * Metadata for a UI resource served by the MCP App.
 */
@Serializable
data class UiResourceMeta(
    val csp: UiResourceCsp? = null,
    val permissions: UiResourcePermissions? = null,
    val domain: String? = null,
    val prefersBorder: Boolean? = null,
)

/** Content-Security-Policy rules for a UI resource. */
@Serializable
data class UiResourceCsp(
    @SerialName("default_src") val defaultSrc: List<String> = emptyList(),
    @SerialName("script_src") val scriptSrc: List<String> = emptyList(),
    @SerialName("style_src") val styleSrc: List<String> = emptyList(),
    @SerialName("connect_src") val connectSrc: List<String> = emptyList(),
    @SerialName("img_src") val imgSrc: List<String> = emptyList(),
)

/** Permission flags required by a UI resource. */
@Serializable
data class UiResourcePermissions(
    val camera: Boolean = false,
    val microphone: Boolean = false,
    val geolocation: Boolean = false,
    val clipboard: Boolean = false,
)
