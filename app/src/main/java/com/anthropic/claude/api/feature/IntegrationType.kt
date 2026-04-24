package com.anthropic.claude.api.feature

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Integration type enum — identifies which third-party integrations are available.
 */
@Serializable
enum class IntegrationType {
    @SerialName("unknown")      UNKNOWN,
    @SerialName("gcal")         GCAL,
    @SerialName("gdrive")       GDRIVE,
    @SerialName("gmail")        GMAIL,
    @SerialName("github")       GITHUB,
    @SerialName("slack")        SLACK,
    @SerialName("asana")        ASANA,
    @SerialName("outline")      OUTLINE,
    @SerialName("salesforce")   SALESFORCE,
    @SerialName("sfdc")         SFDC,
    @SerialName("mcp_resources") MCP_RESOURCES,
}
