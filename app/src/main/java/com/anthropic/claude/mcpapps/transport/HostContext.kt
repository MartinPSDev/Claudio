package com.anthropic.claude.mcpapps.transport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Host context sent to the MCP App on initialization.
 * Contains device, UI and capability information.
 */
@Serializable
data class HostContext(
    val theme: String? = null,
    val platform: String? = null,
    val locale: String? = null,
    @SerialName("timeZone") val timeZone: String? = null,
    @SerialName("userAgent") val userAgent: String? = null,
    @SerialName("displayMode") val displayMode: String? = null,
    @SerialName("availableDisplayModes") val availableDisplayModes: List<String> = emptyList(),
    @SerialName("containerDimensions") val containerDimensions: ContainerDimensions? = null,
    @SerialName("deviceCapabilities") val deviceCapabilities: DeviceCapabilities? = null,
    @SerialName("safeAreaInsets") val safeAreaInsets: SafeAreaInsets? = null,
    val styles: JsonObject? = null,
    @SerialName("toolInfo") val toolInfo: ToolInfo? = null,
)

@Serializable
data class ContainerDimensions(
    val width: Int = 0,
    val height: Int = 0,
)

@Serializable
data class SafeAreaInsets(
    val top: Int = 0,
    val right: Int = 0,
    val bottom: Int = 0,
    val left: Int = 0,
)

@Serializable
data class DeviceCapabilities(
    val camera: Boolean = false,
    val microphone: Boolean = false,
    val geolocation: Boolean = false,
    val notifications: Boolean = false,
)

@Serializable
data class ToolInfo(
    @SerialName("tool_name") val toolName: String? = null,
    @SerialName("server_name") val serverName: String? = null,
)
