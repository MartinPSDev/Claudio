package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Web search tool variant descriptor.
 * Known values: "web_search", "web_search_v0".
 */
@Serializable
data class WebSearchTool(
    val name: String = "web_search",
    val type: String = "web_search_v0",
)
